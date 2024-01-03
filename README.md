# Patient Hub Project

## Brief Overview

The Patient Hub project is a Spring Boot-based web application designed to manage patient information. It provides CRUD (Create, Read, Update, Delete) operations for patients and utilizes caching for efficient data retrieval. The project is built using Java, Spring Boot, and incorporates a MySQL database.

## Technology Stack

- **Java**: Core programming language.
- **Spring Boot**: Framework for building Java-based applications.
- **MySQL**: Relational database for storing patient data.
- **Docker**: Containerization tool for easy deployment.
- **Swagger**: API documentation tool for clear and interactive documentation.
- **Postman**: API testing and development environment.

## Docker Run Commands
### Development Environment
```bash
docker-compose -f docker-compose.dev.yml up --build
```
### Development Environment  
```bash
docker-compose -f docker-compose.qa.yml up --build
```
### Prod Environment
```bash
docker-compose -f docker-compose.prod.yml up --build
```

## Feel free to explore the API using Swagger documentation or Postman collections.  

- Swagger documentation can be accessed locally via http://localhost:8080/swagger-ui.html  
- Download Postman collection from https://github.com/snehayv-1998/Patient-Hub/blob/main/Patient%20hub.postman_collection.json

## Running the Application locally

- Ensure you have the necessary technologies installed.
- Clone the repository: https://github.com/snehayv-1998/Patient-Hub.git
- Navigate to the project directory: cd Patient-Hub
- Build the application
- Run the application
- The application will be accessible at http://localhost:8080.
  
