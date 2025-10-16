# task1-java-rest-api

# Task 1: Java REST API with MongoDB

**Name:** Santhosh S
**Date:** October 16, 2025  
**Assessment:** Kaiburr Task 1

---

## Project Overview

A Spring Boot REST API application for managing and executing shell command tasks with MongoDB persistence. The application provides endpoints to create, retrieve, search, execute, and delete tasks.

## Technology Stack

- **Java:** 17
- **Framework:** Spring Boot 3.1.5
- **Database:** MongoDB
- **Build Tool:** Maven 3.9+
- **Container:** Docker

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB (via Docker or local installation)
- Docker Desktop (optional, for containerized MongoDB)

---

## Installation & Setup

### 1. Clone the Repository

- git clone https://github.com/NOTSANTOSH04/task1-java-rest-api.git


### 2. Start MongoDB

Using Docker:
-docker run -d -p 27017:27017 --name mongodb mongo:latest


Verify MongoDB is running:
docker ps


### 3. Build the Project
mvn clean install


### 4. Run the Application
mvn spring-boot:run


The application will start on `http://localhost:8081`

---

## API Endpoints

### 1. Get All Tasks

**Endpoint:** `GET /api/tasks`

**Description:** Retrieves all tasks from the database.

**Example Request:**
GET http://localhost:8081/api/tasks

**Screenshot:**

<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/4a9e0ad8-ae20-4bbe-be26-88754e8c8e58" />


---

### 2. Get Task by ID

**Endpoint:** `GET /api/tasks?id={id}`

**Description:** Retrieves a specific task by its ID.


**Screenshot:**

**Example Request:**
Get http://localhost:8081/api/tasks?id=1

<img width="1919" height="1078" alt="image" src="https://github.com/user-attachments/assets/fb7658c6-a583-4e21-9c41-b943b2b6e02a" />


Get http://localhost:8081/api/tasks?id=404

<img width="1918" height="1078" alt="image" src="https://github.com/user-attachments/assets/b048cfd7-9e36-4872-9d6c-97a5583dd813" />


### 3. Create Task

**Endpoint:** `PUT /api/tasks`

**Description:** Creates a new task in the database.

**Request Body:**  select raw and json under body

{
"id": "123",
"name": "hello World",
"owner": "Santhosh",
"command": "echo Hello world! again"
}

<img width="1919" height="1078" alt="image" src="https://github.com/user-attachments/assets/9204320b-c9aa-4b02-9565-7965fba33bcb" />


### 4. Search Tasks by Name

**Endpoint:** `GET /api/tasks/search?name={name}`

**Description:** Searches for tasks by name (case-insensitive, partial match).

**Example Request:**

Get http://localhost:8081/api/tasks/search?name=print

<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/a8cacb51-6e63-4714-bf9c-094aa3015517" />


Get http://localhost:8081/api/tasks/search?name=hello

<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/44d02731-3392-4733-9a8c-46010f4d7a45" />


### 5. Execute Task

**Endpoint:** `PUT /api/tasks/{id}/execute`

**Description:** Executes the command associated with the task and stores the output.

**Example Request:**

PUT http://localhost:8081/api/tasks/123/execute

<img width="1919" height="1078" alt="image" src="https://github.com/user-attachments/assets/156b71c8-cd22-4f6b-afa1-1c71da4c40f5" />


### 6. Delete Task

**Endpoint:** `DELETE /api/tasks/{id}`

**Description:** Deletes a task from the database.

**Example Request:**

DELETE http://localhost:8081/api/tasks/123

<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/d10afe2f-8b2a-4afe-85df-4928a84118b3" />

GET http://localhost:8081/api/tasks?id=123

<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/8d53d9ff-7e27-414d-89c3-bff1ef4a3862" />


## Application Running

<img width="1837" height="839" alt="image" src="https://github.com/user-attachments/assets/d0f51608-0871-44e4-babb-05a9e3bf20e3" />

<img width="1841" height="881" alt="image" src="https://github.com/user-attachments/assets/3216642e-3175-4d1b-a3df-a851240e522b" />



*Screenshot showing the Spring Boot application successfully started and listening on port 8081*

## MongoDB Database

<img width="1846" height="1075" alt="image" src="https://github.com/user-attachments/assets/126d4251-2a7d-4cc3-80e2-fe8fd57ff7af" />

*Screenshot showing tasks stored in MongoDB*


---

## Configuration

**Application Properties** (`src/main/resources/application.properties`):

