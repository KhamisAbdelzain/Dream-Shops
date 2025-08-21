# Dream Shops 🛍️

**E‑commerce App (Spring Boot)**

A clean, modular e‑commerce backend built with Spring Boot. This repository contains RESTful APIs for product, category, image, cart, order and user management — ready to be used by a frontend (mobile/web) application.

---

## Table of Contents

* [Project Overview](#project-overview)
* [Key Features](#key-features)
* [Tech Stack](#tech-stack)
* [Quick Start](#quick-start)

  * [Requirements](#requirements)
  * [Configuration](#configuration)
  * [Build & Run](#build--run)
  * [Docker (example)](#docker-example)
* [API (Summary)](#api-summary)
* [Authentication](#authentication)
* [File uploads & limits](#file-uploads--limits)
* [Database](#database)
* [Swagger / API docs](#swagger--api-docs)
* [Contributing](#contributing)
* [License & Contact](#license--contact)

---

## Project Overview

Dream Shops provides a backend for a modern e‑commerce app. It exposes a well documented REST API for managing categories, products, images, carts, cart items, orders and users. Designed for extensibility and production readiness using Spring Boot, JPA, JWT authentication and OpenAPI/Swagger UI.

## Key Features

* Product CRUD and search (by category, brand, name)
* Category management
* Image upload/download endpoints
* Cart and CartItem operations (add, update, remove)
* Order creation and retrieval
* JWT-based authentication
* OpenAPI / Swagger UI for interactive API exploration
* MySQL persistence via Spring Data JPA

## Tech Stack

* Java 24
* Spring Boot 3.5.x
* Spring Web, Spring Data JPA, Spring Security, Validation
* springdoc-openapi (Swagger UI)
* JJWT (Java JWT) for token signing
* ModelMapper for DTO mapping
* Lombok to reduce boilerplate
* MySQL (mysql-connector-j)
* Maven for build

## Quick Start

### Requirements

* Java 24 JDK
* Maven 3.9+
* MySQL server
* (Optional) Docker

### Configuration

This project reads configuration from `application.properties` (or environment variables). Typical properties you need to set:

```properties
spring.application.name=dream-shops
server.port=9192

spring.datasource.url=jdbc:mysql://localhost:3306/dream_shops_db
spring.datasource.username=root
spring.datasource.password=your_db_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB

api.prefix=/api/v1

auth.token.expirationInMils=3600000
# DO NOT hardcode jwt secret in version control. Use environment variable or secrets manager.
# auth.token.jwtSecret=<replace-with-secure-base64-or-random-key>
```

**Environment example (.env):**

```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/dream_shops_db
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=secret
AUTH_TOKEN_JWTSECRET=replace_with_secure_value
AUTH_TOKEN_EXPIRATIONINMILS=3600000
```

> Replace `AUTH_TOKEN_JWTSECRET` with a strong, random value and never commit it to source control.

### Build & Run

Build with Maven:

```bash
mvn clean package -DskipTests
```

Run jar:

```bash
java -jar target/dream-shops-0.0.1-SNAPSHOT.jar
```

You can also run via your IDE (run the Spring Boot application class).

### Docker (example)

A simple multi-stage Dockerfile approach:

```dockerfile
# Build
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
```

Build and run:

```bash
docker build -t dream-shops:latest .
docker run -e SPRING_DATASOURCE_URL='jdbc:mysql://host.docker.internal:3306/dream_shops_db' \
  -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=secret \
  -p 9192:9192 dream-shops:latest
```

## API (Summary)

> This is a high-level summary of controllers & operations. Use Swagger UI for full details and example payloads.

### Categories

* `GET  /api/v1/category/{id}/category`
* `PUT  /api/v1/category/{id}/update`
* `POST /api/v1/category/add`
* `GET  /api/v1/category/{name}/category`
* `GET  /api/v1/category/all`
* `DELETE /api/v1/category/{id}/delete`

### Images

* `PUT    /api/v1/images/{imageId}/update`
* `POST   /api/v1/images/upload`
* `GET    /api/v1/images/download/{imageId}`
* `DELETE /api/v1/images/{imageId}/delete`

### Products

* `PUT    /api/v1/products/{id}/update`
* `POST   /api/v1/products/add`
* `GET    /api/v1/products/{id}`
* `GET    /api/v1/products/byCategory/{id}`
* `GET    /api/v1/products/byCategoryAndName` (query params)
* `GET    /api/v1/products/byBrandAndName` (query params)
* `GET    /api/v1/products/byBrand/{id}`
* `GET    /api/v1/products/byName` (query params)
* `GET    /api/v1/products/all`
* `DELETE /api/v1/products/{id}/delete`

### Orders

* `POST /api/v1/order/addOrder`
* `GET  /api/v1/order/{id}`
* `GET  /api/v1/order/all`

### Auth

* `POST /api/v1/auth/{id}`  (login / token issuance)

### Cart

* `POST   /api/v1/cart/addCart`
* `GET    /api/v1/cart/{id}/get-cart`
* `GET    /api/v1/cart/{id}/total-price`
* `DELETE /api/v1/cart/{id}/delete`

### CartItems

* `PUT    /api/v1/cartItems/{cartId}/{itemId}/update`
* `POST   /api/v1/cartItems/add`
* `DELETE /api/v1/cartItems/{cartId}/{itemId}/remove`

### Users

* `PUT    /api/v1/users/{id}/update`
* `GET    /api/v1/users/{id}`
* `DELETE /api/v1/users/{id}/delete`

> For request/response shapes, required fields and examples, open the Swagger UI (see below).

## Authentication

The API uses JWT tokens. Typical flow:

1. Client calls login endpoint (`/api/v1/auth/...`) with credentials.
2. Server returns a signed JWT with expiration defined by `auth.token.expirationInMils`.
3. Include `Authorization: Bearer <token>` header on protected endpoints.

**Security notes:**

* Keep the JWT signing secret (`auth.token.jwtSecret`) private. Use environment secrets or a vault in production.
* Use HTTPS in production to protect tokens in transit.

## File uploads & limits

Configured limits in properties:

* `spring.servlet.multipart.max-file-size=5MB`
* `spring.servlet.multipart.max-request-size=5MB`

Adjust these values in `application.properties` or as environment variables if you expect larger images.

## Database

* Uses MySQL via Spring Data JPA.
* `spring.jpa.hibernate.ddl-auto=update` (convenient for dev). For production, prefer `validate` or a managed migration strategy (Flyway / Liquibase).

## Swagger / API docs

SpringDoc is included. Once the app is running you can access the interactive docs (default path):

```
http://localhost:9192/swagger-ui/index.html
```

Use the UI to see request/response models and to test endpoints live.

## Screenshots

Include one or more screenshots of Swagger UI or Postman examples. Example markdown:

```markdown
![Swagger UI screenshot](./docs/swagger.png)
```



