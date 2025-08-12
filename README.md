# Fork & Spoon - A Microservices Food Delivery Platform

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.x-brightgreen)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-black)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue)
![JWT](https://img.shields.io/badge/Security-JWT-purple)

This project is a fully functional, distributed food delivery platform built from the ground up using a microservices architecture with Spring Boot. It demonstrates key concepts in modern backend development, including service discovery, API gateway, asynchronous communication, and stateless security.

---

## Architecture

The system is decomposed into independent, domain-specific microservices that communicate with each other through a combination of synchronous REST APIs and asynchronous events via Kafka. All external traffic is routed and secured through a central API Gateway.



---

## Features

- **Service Discovery**: Services register with a Eureka server, allowing for dynamic scaling and resilience.
- **Centralized Entry Point**: A Spring Cloud API Gateway handles all incoming requests, routing them to the appropriate service.
- **Stateless Security**: End-to-end security using Spring Security and JSON Web Tokens (JWT). The API Gateway validates tokens for all protected routes.
- **Asynchronous Messaging**: The Order Service communicates with the Notification Service asynchronously using Apache Kafka, creating a decoupled and resilient system.
- **User Management**: Full user registration and login functionality with secure, hashed passwords (BCrypt).
- **Role-Based Authorization**: Endpoints are protected based on user roles (e.g., `ROLE_USER` vs. `ROLE_ADMIN`).
- **Core Business Logic**: Full implementation of Customer, Restaurant, and Order management services.

---

## Technology Stack

- **Framework**: Spring Boot
- **Language**: Java 17
- **Microservice Patterns**:
    - **Service Discovery**: Spring Cloud Netflix Eureka
    - **API Gateway**: Spring Cloud Gateway
    - **Security**: Spring Security 6, JWT
- **Messaging**: Apache Kafka
- **Database**: PostgreSQL
- **Build Tool**: Maven

---

## Project Structure

This project uses a monorepo structure to manage all the microservices.

    📁 fork-and-spoon-microservices/
    ├── 📁 api-gateway/
    ├── 📁 customer-service/
    ├── 📁 notification-service/
    ├── 📁 order-service/
    ├── 📁 restaurant-service/
    └── 📁 service-registry/

---

## Getting Started

### Prerequisites

- JDK 17 or higher
- Apache Maven
- PostgreSQL running locally
- Apache Kafka running locally (on `localhost:9092`)

### Configuration

This project uses environment variables to manage sensitive configuration. Before running, you must set the following variables in your IDE's run configuration for each service:

- `DB_USERNAME`: Your PostgreSQL username.
- `DB_PASSWORD`: Your PostgreSQL password.
- `JWT_SECRET_KEY`: A long, Base64-encoded secret key for signing JWTs.

### Running the Application

The services must be started in the following order:

1.  **Service Registry**
2.  **Customer Service**
3.  **Restaurant Service**
4.  **Order Service**
5.  **Notification Service**
6.  **API Gateway**

---

## API Endpoints

All requests should be sent to the **API Gateway** on port `8080`.

| Method | Endpoint                             | Protection | Description                          |
| ------ | ------------------------------------ | ---------- | ------------------------------------ |
| `POST` | `/api/v1/customers`                  | **Public** | Register a new customer.             |
| `POST` | `/api/v1/auth/login`                 | **Public** | Log in to receive a JWT.             |
| `GET`  | `/api/v1/restaurants`                | **USER** | Get a list of all restaurants.       |
| `POST` | `/api/v1/restaurants`                | **ADMIN** | Add a new restaurant.                |
| `POST` | `/api/v1/orders`                     | **USER** | Place a new order.                   |