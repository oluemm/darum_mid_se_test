# 🧩 Employee Management System – Microservices Architecture

This project is a **Spring Boot Microservices** implementation of an **Employee Management System**, designed with modularity, scalability, and maintainability in mind.

Each microservice performs a distinct responsibility and communicates via REST APIs.  
Configuration is centralized through a Config Server, and service discovery is managed by a Eureka Discovery Server.

---

## Table of Contents
1. [Project Overview](#-project-overview)
2. [Services Description](#-services-description)
3. [Technologies Used](#-technologies-used)
4. [Quick Start Guide](./QuickStart.md)


## 🏗️ Project Overview

### 📁 Project Structure


---

## ⚙️ Services Description

### 🧭 1. Discovery Service (`discovery-service`)
- **Tech:** Spring Cloud Netflix Eureka
- **Purpose:** Registers and manages all microservices for dynamic service discovery.
- **Port:** `8761`
- **Access UI:** [http://localhost:8761](http://localhost:8761)

---

### 🛠️ 2. Config Server (`config-server`)
- **Tech:** Spring Cloud Config
- **Purpose:** Provides centralized configuration for all services from a single repository or local config directory.
- **Port:** `4000`

> Each service loads its configuration from this server during startup.

---

### 🔐 3. Auth Service (`auth-service`)
- **Tech:** Spring Boot, Spring Security, JPA, PostgreSQL
- **Purpose:** Handles user authentication, registration, and authorization.
- **Notes:**
    - Includes **demo data** for quick testing.
    - Supports endpoints to create new users and roles.
- **Port:** _Set in config server_

---

### 👥 4. Employee Service (`employee-service`)
- **Tech:** Spring Boot, Spring Data JPA, PostgreSQL
- **Purpose:** Manages employee records (CRUD operations, data queries).
- **Notes:**
    - Includes **demo data** for initial testing.
    - Supports adding new employees via REST endpoints.
- **Port:** _Set in config server_

---

### 🌉 5. API Gateway (`api-gateway`)
- **Tech:** Spring Cloud Gateway
- **Purpose:** Serves as a unified entry point for routing requests to backend microservices.
- **Port:** _Set in config server_

> Example routes:
> - `/api/auth/**` → Auth Service
> - `/api/employees/**` & `/api/department/**` → Employee Mgmt. Service

---

## 🧰 Technologies Used

- **Spring Boot 3+**
- **Spring Cloud (Eureka, Config, Gateway)**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Lombok** – for reducing boilerplate (getters/setters, constructors)
- **MapStruct** – for DTO ↔ Entity mapping
- **Maven** – for build and dependency management
- **Java 17+**

---

## 🚀 Getting Started

### ✅ Prerequisites

Ensure the following are installed:

- Java 17 or higher
- Maven 3.9+
- PostgreSQL (running locally or remotely)

---

### 🧾 1. Clone the Repository

```bash
git clone https://github.com/oluemm/darum_mid_se_test
cd darum_mid_se_test
```

---
📘 **Need setup instructions?**  
👉 See the [Quick Start Guide](./QuickStart.md) for build steps, environment setup, and run commands.
---
