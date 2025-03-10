# The Sorting Hat

## Overview

The Sorting Hat is a Spring Boot application that provides authentication and authorization services. Named after the magical artifact from the Harry Potter universe, this service handles user registration, login, and token management for the Diagon Alley system.

## Features

- User registration and signup
- Secure login with JWT token authentication
- User management (update, delete)
- Password management
- Kafka integration for email notifications
- API documentation with Swagger/OpenAPI

## Tech Stack

- Java 17
- Spring Boot 3.4.2
- Spring Security
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- JWT for authentication
- Swagger/OpenAPI for API documentation

## Prerequisites

- JDK 17 or higher
- Maven 3.8+
- PostgreSQL 13+
- Kafka 3.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Installation

### Environment Setup

1. Install JDK 17

   ```bash
   # For Ubuntu/Debian
   sudo apt install openjdk-17-jdk
   
   # For macOS (using Homebrew)
   brew install openjdk@17
   ```

2. Install PostgreSQL ─ I have ran this docker-compose file to spin up ```Postgres``` and ```PgAdmin```

    ```yml
        services:
            postgresdb:
                container_name: postgrescontainer
                image: postgres:16.1
                restart: always
                environment:
                    POSTGRES_USER: ${POSTGRES_USER}
                    POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
                    POSTGRES_DB: ${POSTGRES_DB}
                expose:
                    - 5432
                ports:
                    - 5432:5432
                volumes:
                    - postgresvolume:/var/lib/postgresql/data
                - ./schema.sql:/docker-entrypoint-initdb.d/schema.sql

            pgadmin:
                container_name: pgadmincontainer
                image: dpage/pgadmin4:latest
                restart: always
                environment:
                    PGADMIN_DEFAULT_EMAIL: ${PGADMIN_EMAIL}
                    PGADMIN_DEFAULT_PASSWORD: ${PGADMIN_PASSWORD}
                    PGADMIN_DEFAULT_ADDRESS: 6000
                    PGADMIN_LISTEN_PORT: 6000
                expose:
                    - 6000
                ports:
                    - 7000:6000
                volumes:
                    - pgadminvolume:/var/lib/pgadmin

            volumes:
                pgadminvolume:
                postgresvolume:
    ```

3. Install Kafka ─ I have followed this [link](https://hub.docker.com/r/bitnami/kafka) to install kafka in docker

    ```sh
    docker run -d --name kafka-server \
        -p 9092:9092 --network app-tier \
        -e KAFKA_CFG_NODE_ID=0 \
        -e KAFKA_CFG_PROCESS_ROLES=controller,broker \
        -e KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 \
        -e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
        -e KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT \
        -e KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=0@kafka-server:9093 \
        -e KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER \
        bitnami/kafka:latest
    ```

### Environment Variables

The application requires the following environmental variables:

- `DB_USER`: PostgreSQL database username
- `DB_PASS`: PostgreSQL database password
- `JWT_SECRET`: Secret key for JWT token generation

For Docker Compose setup, additional variables:

- `POSTGRES_USER`: PostgreSQL container username
- `POSTGRES_PASSWORD`: PostgreSQL container password
- `POSTGRES_DB`: PostgreSQL database name (hogwards_records)
- `PGADMIN_EMAIL`: PGAdmin email
- `PGADMIN_PASSWORD`: PGAdmin password

#### Setting Environment Variables

For Linux/macOS:

```bash
export DB_USER=your_database_username
export DB_PASS=your_database_password
export JWT_SECRET=your_jwt_secret_key
```

For Windows Command Prompt:

```cmd
set DB_USER=your_database_username
set DB_PASS=your_database_password
set JWT_SECRET=your_jwt_secret_key
```

For Windows PowerShell:

```powershell
$env:DB_USER="your_database_username"
$env:DB_PASS="your_database_password"
$env:JWT_SECRET="your_jwt_secret_key"
```

### Application Setup

1. Clone the repository

   ```sh
        git clone https://github.com/the-leaky-cauldron/the-sorting-hat
        cd thesortinghat
   ```

2. Build the application

    ```sh
        ./mvnw clean package -DskipTests
    ```

3. Run the application

    ```sh
        # If using Maven
        mvn spring-boot:run

        # If using Gradle
        ./gradlew bootRun
    ```

The application will start on port 8081 by default.

## API Documentation

API endpoints are documented using Swagger/OpenAPI. After starting the server, visit ```/swagger-ui.html``` to view the documentation.

## Acknowledgments

- Inspired by J.K. Rowling's Harry Potter series
- Special thanks to all contributors
- Scaler Academy for project guidance
- Spring Boot and related open-source communities

## Contact

- Project Maintainer: [Vijaysurya Mandala](https://github.com/mandalavijaysurya)
- Project Repository: [GitHub](https://github.com/the-leaky-cauldron/the-sorting-hat)
- Issue Tracker: [GitHub Issues](https://github.com/the-leaky-cauldron/the-sorting-hat/issues)
