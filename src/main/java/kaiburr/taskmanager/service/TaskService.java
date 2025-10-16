package kaiburr.taskmanager.service;

import kaiburr.taskmanager.model.Task;
import kaiburr.taskmanager.model.TaskExecution;
import kaiburr.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    // Unsafe command patterns to block
    private static final Pattern[] UNSAFE_PATTERNS = {
        Pattern.compile(".*rm\\s+-rf.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*\\|\\s*sh.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*\\|\\s*bash.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*>\\s*/dev/.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*wget.*\\|.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*curl.*\\|.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*sudo.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*chmod.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*chown.*", Pattern.CASE_INSENSITIVE)
    };
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }
    
    public List<Task> findTasksByName(String name) {
        return taskRepository.findByNameContaining(name);
    }
    
    public Task createTask(Task task) throws IllegalArgumentException {
        validateCommand(task.getCommand());
        return taskRepository.save(task);
    }
    
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }
    
    public Task executeTask(String taskId) throws Exception {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (!taskOpt.isPresent()) {
            throw new IllegalArgumentException("Task not found");
        }
        
        Task task = taskOpt.get();
        validateCommand(task.getCommand());
        
        Date startTime = new Date();
        StringBuilder output = new StringBuilder();
        
        try {
            // Detect operating system
            String os = System.getProperty("os.name").toLowerCase();
            Process process;
            
            if (os.contains("win")) {
                // Windows - use cmd.exe
                process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", task.getCommand()});
            } else {
                // Linux/Mac - use /bin/sh
                process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", task.getCommand()});
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                output.append("ERROR: ").append(line).append("\n");
            }
            
            process.waitFor();
            
        } catch (Exception e) {
            output.append("Execution failed: ").append(e.getMessage());
        }
        
        Date endTime = new Date();
        
        TaskExecution execution = new TaskExecution(startTime, endTime, output.toString());
        task.getTaskExecutions().add(execution);
        
        return taskRepository.save(task);
    }
    
    private void validateCommand(String command) throws IllegalArgumentException {
        if (command == null || command.trim().isEmpty()) {
            throw new IllegalArgumentException("Command cannot be empty");
        }
        
        for (Pattern pattern : UNSAFE_PATTERNS) {
            if (pattern.matcher(command).matches()) {
                throw new IllegalArgumentException("Command contains unsafe operations");
            }
        }
    }
}
