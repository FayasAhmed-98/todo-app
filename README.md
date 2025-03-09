# Todo Task Web Application

## Overview
This is a Full Stack To-Do Task Web Application that allows users to Create tasks with a title and description , View the 5 most recent tasks , Mark tasks as completed, which removes them from the UI

## Features
- Add new tasks with a title and description.
- View the latest five tasks.
- Mark tasks as completed.
- Backend built with Spring Boot and MySQL.
- Frontend built with React.
- Dockerized for easy deployment.

## Tech Stack
- **Frontend:** React, JavaScript
- **Backend:** Java, Spring Boot, REST API
- **Database:** MySQL
- **Containerization:** Docker, Docker Compose

## Setup Instructions
### Prerequisites
Make sure you have the following installed:
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

### Running the Application
1. Clone the repository:
   ```sh
   git clone https://github.com/FayasAhmed-98/todo-app
   cd todo-app
   ```
2. Build and start the application using Docker:
   ```sh
   docker-compose up --build
   ```
3. The application should now be running:
   - **Backend API:** `http://localhost:8080`
   - **Frontend UI:** `http://localhost:3000`
   - **MySQL Database:** Exposed on port `3306`

### API Endpoints
| Method | Description                 |
|--------|-----------------------------|
| POST   | Create a new task           |
| GET    | Retrieve the latest 5 tasks |
| PUT    | Mark a task as completed    |

## Cleanup
To stop and remove all containers:
```sh
docker-compose down
```

## Contributing
Feel free to fork the repository and submit pull requests.

## License
This project is for assessment purposes only.


