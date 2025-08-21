

# DreamShops 🛍️

A Spring Boot–based **E-Commerce REST API** that provides user management and product management features.

## 🚀 Features

* User Management (CRUD)
* Product Management (CRUD + advanced filtering)
* Role-based Authorization (Admin/User)
* Exception Handling with custom responses
* DTO pattern for clean responses
* Secure APIs with Spring Security

---

## 🛠️ Tech Stack

* **Java 21**
* **Spring Boot 3**
* **Spring Security**
* **Spring Data JPA (Hibernate)**
* **Maven**
* **MySQL / PostgreSQL** (configurable)
* **Lombok**

---

## 📦 Requirements

* JDK 17+ (preferably Java 21)
* Maven 3.9+
* MySQL or PostgreSQL database running
* Docker (optional, for containerized deployment)

---

## ⚙️ Setup & Run

### Clone the Repository

```bash
git clone https://github.com/<your-username>/dreamshops.git
cd dreamshops
```

### Configure Database

Update `application.properties` (or `application.yml`) with your DB credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/dreamshops
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### Run with Maven

```bash
mvn spring-boot:run
```

### Build JAR

```bash
mvn clean package -DskipTests
java -jar target/dreamshops-0.0.1-SNAPSHOT.jar
```

### Run with Docker (Optional)

```bash
docker build -t dreamshops .
docker run -p 8080:8080 dreamshops
```

---

## 📖 API Documentation

> Base URL: `http://localhost:8080/api/v1` (depends on `${api.prefix}` in properties)

### 🛍️ Product APIs

| Method   | Endpoint                                                                  | Description                    | Auth Required |
| -------- | ------------------------------------------------------------------------- | ------------------------------ | ------------- |
| `GET`    | `/products/product/{id}`                                                  | Get product by ID              | No            |
| `GET`    | `/products/all`                                                           | Get all products               | No            |
| `GET`    | `/products/all/{name}`                                                    | Search products by name        | No            |
| `GET`    | `/products/product/by/brand-and-name?brand=Samsung&name=Phone`            | Filter by brand & name         | No            |
| `GET`    | `/products/product/by/category-and-brand?category=Electronics&brand=Sony` | Filter by category & brand     | No            |
| `GET`    | `/products/product/{category}/product`                                    | Get products by category       | No            |
| `GET`    | `/products/product/brands?brand=Apple`                                    | Get products by brand          | No            |
| `GET`    | `/products/product/count/by-brand/and-name?brand=LG&name=TV`              | Count products by brand & name | No            |
| `POST`   | `/products/add`                                                           | Add a new product              | ✅ Admin       |
| `PUT`    | `/products/product/{id}/update`                                           | Update product by ID           | ✅ Admin       |
| `DELETE` | `/products/product/{id}/delete`                                           | Delete product by ID           | ✅ Admin       |

---

### 👤 User APIs

| Method   | Endpoint                 | Description          |
| -------- | ------------------------ | -------------------- |
| `GET`    | `/users/{id}/user`       | Get user by ID       |
| `POST`   | `/users/add`             | Create new user      |
| `PUT`    | `/users/{userId}/update` | Update existing user |
| `DELETE` | `/users/{userId}/delete` | Delete user          |

---

## 🔒 Authentication & Roles

* **ROLE\_USER** → Can browse products, view details.
* **ROLE\_ADMIN** → Full CRUD access to products and users.

---

## 📌 Contribution

1. Fork the repo
2. Create a new branch (`feature/xyz`)
3. Commit your changes
4. Push & create PR

---


