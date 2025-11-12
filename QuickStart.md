# 🚀 Running the Employee Management System

This guide explains how to **build and run** all microservices in the **Employee Management System** locally using **Maven** commands.  
No Docker setup is required — each service can be run individually from the terminal or your IDE.

---

## 🧱 1. Prerequisites

Before you begin, ensure the following tools are installed:

| Tool | Version | Description |
|------|----------|-------------|
| ☕ Java | 17 or later | Required runtime for Spring Boot 3 |
| 🛠️ Maven | 3.9+ | Build and dependency manager |
| 🐘 PostgreSQL | 13+ | Database for Auth and Employee services |

---

## 🗂️ 2. Build the Project

From the **root folder (`darum_mid_se_test/`)**, run:

```bash
mvn clean install -DskipTests
```

## 🧩 3. Start Order (Very Important)

Microservices depend on each other.
Follow this startup order to avoid connection errors.

### 1️⃣ Config Server
```bash
cd config-server
mvn spring-boot:run
```
_Loads centralized configs._

### 2️⃣ Discovery Service

```bash
cd ../discovery-service
mvn spring-boot:run
```
- Registers all services dynamically.
- Runs on port 8761.
- Access UI: http://localhost:8761

### 3️⃣ Auth Service
```bash
cd ../auth-service
mvn spring-boot:run
```
- Connects to PostgreSQL.
- Loads demo data (users, roles).

### 4️⃣ Employee Service
```bash
cd ../employee-service
mvn spring-boot:run
```
- Connects to PostgreSQL.
- Loads demo data (employees).

### 5️⃣ API Gateway
```bash
cd ../api-gateway
mvn spring-boot:run
```
-Routes all requests through /auth/** and /employees/**.

## Common Maven Commands
| Action                  | Command                                              |
|-------------------------| ---------------------------------------------------- |
| Compile Project         | `mvn clean compile`                                  |
| Clean & build all       | `mvn clean install`                                  |
| Run specific service    | `mvn spring-boot:run`                                |
| Run with profile        | `mvn spring-boot:run -Dspring-boot.run.profiles=dev` |
| Check Flyway migrations | `mvn flyway:info`                                    |
| Run migrations manually | `mvn flyway:migrate`                                 |
