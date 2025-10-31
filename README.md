# Store
Order management on store inventory using Spring Boot, JPA, Web and H2 database.

## 🚀 Features

- **Spring Boot 3.5.4** - Latest stable version with enhanced performance and security
- **Java 21** - Modern Java with latest language features
- **Spring Data JPA** - Database abstraction layer with Hibernate ORM
- **Spring Web** - RESTful web services and MVC support
- **H2 Database** - In-memory database 
- **Lombok** - Reduces boilerplate code with annotations
- **Maven Wrapper** - Ensures consistent Maven version across environments
- **Spring Boot Test** - Comprehensive testing framework integration

## 📋 Prerequisites

- **Java 21** or higher
- **Maven 3.6+** (or use included Maven wrapper)

## 🛠️ Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd store
```

### 2. Run with Maven Wrapper

```bash
# On Linux/macOS
./mvnw spring-boot:run

# On Windows
mvnw.cmd spring-boot:run

### 3. Alternative: Run with Local Maven

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🧪 Testing

### Run All Tests

```bash
./mvnw test
```

## 📦 Building

### Create JAR file

```bash
./mvnw clean package
```

## 📚 Available Endpoints

Currently, the application provides:

- **Health Check**: `GET http://localhost:8080/api/test` 
- **H2 Console**: `http://localhost:8080/h2-console` 
- **More endpoints**: please see: CURL_Cheatsheat.txt