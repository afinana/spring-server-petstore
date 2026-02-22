# 🐾 Spring Petstore API

A production-ready REST API for the classic **Petstore** sample application, built with **Spring Boot 3** and **Redis**.  
It exposes the standard `/v2/pet`, `/v2/store`, and `/v2/user` endpoints, is fully documented via OpenAPI 3 (Swagger UI), and ships as a multi-arch Docker image via GitHub Actions.

---

## 📋 Table of Contents

- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Run Locally](#run-locally)
  - [Run with Docker Compose](#run-with-docker-compose)
  - [Run with Docker](#run-with-docker)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [API Documentation (Swagger UI)](#api-documentation-swagger-ui)
- [Running Tests](#running-tests)
- [Docker Build](#docker-build)
- [CI/CD](#cicd)
- [Project Structure](#project-structure)

---

## Tech Stack

| Layer            | Technology                          |
|------------------|-------------------------------------|
| Language         | Java 21 (Virtual Threads enabled)   |
| Framework        | Spring Boot 3.3                     |
| Database         | Redis (Spring Data Redis)           |
| API Docs         | SpringDoc OpenAPI 3 / Swagger UI    |
| Security         | Spring Security + JWT (Auth0 java-jwt) |
| Caching          | Caffeine (in-memory, 10 min TTL)    |
| Mapping          | MapStruct                           |
| Boilerplate      | Lombok                              |
| Build            | Maven 3.9+                          |
| Containerisation | Docker (multi-stage, multi-arch)    |
| CI/CD            | GitHub Actions                      |

---

## Prerequisites

| Tool        | Minimum version |
|-------------|-----------------|
| Java (JDK)  | 21              |
| Maven       | 3.9             |
| Redis       | 6+              |
| Docker      | 24+ *(optional)*|

---

## Getting Started

### Run Locally

1. **Clone the repository**

   ```bash
   git clone https://github.com/<your-org>/spring-server-petstore.git
   cd spring-server-petstore
   ```

2. **Start Redis** (Docker is the quickest way):

   ```bash
   docker run -d --name redis -p 6379:6379 redis:latest
   ```

3. **Build and run**

   ```bash
   mvn spring-boot:run
   ```

   The server starts on **<http://127.0.0.1:8080>** by default.

---

### Run with Docker Compose

Create a `docker-compose.yml` alongside this project (or use the one in the repository if present):

```yaml
services:
  api:
    image: <dockerhub-user>/spring-redis-server-petstore:redis
    ports:
      - "8080:8080"
    environment:
      REDIS_HOST: redis
      REDIS_PORT: 6379
    depends_on:
      - redis

  redis:
    image: redis:latest
    ports:
      - "6379:6379"
```

```bash
docker compose up -d
```

---

### Run with Docker

```bash
# Build
docker build -t spring-server-petstore .

# Run (point to an existing Redis instance)
docker run -p 8080:8080 \
  -e REDIS_HOST="host.docker.internal" -e REDIS_PORT="6379" \
  spring-server-petstore
```

---

## Configuration

All settings can be overridden with **environment variables** (or JVM `-D` flags). Defaults are suitable for local development.

| Environment Variable | Default                                                    | Description                      |
|----------------------|------------------------------------------------------------|----------------------------------|
| `SERVER_ADDR`        | `127.0.0.1`                                               | Server bind address              |
| `SERVER_PORT`        | `8080`                                                     | Server HTTP port                 |
| `REDIS_HOST`         | `localhost`                                                | Redis connection host            |
| `REDIS_PORT`         | `6379`                                                     | Redis connection port            |

Additional settings (cache TTL, actuator exposure, etc.) live in `src/main/resources/application.yaml`.

---

## API Endpoints

### Pet — `/v2/pet`

| Method | Path                       | Description                     |
|--------|----------------------------|---------------------------------|
| `GET`  | `/v2/pet`                  | Return all pets                 |
| `POST` | `/v2/pet`                  | Add a new pet                   |
| `PUT`  | `/v2/pet`                  | Update an existing pet          |
| `GET`  | `/v2/pet/findByStatus`     | Find pets by status             |
| `GET`  | `/v2/pet/findByTags`       | Find pets by tags               |
| `GET`  | `/v2/pet/{petId}`          | Find pet by ID                  |
| `POST` | `/v2/pet/{petId}`          | Update pet with form data       |
| `DELETE`| `/v2/pet/{petId}`         | Delete a pet                    |

### Store — `/v2/store`

| Method | Path                       | Description                     |
|--------|----------------------------|---------------------------------|
| `GET`  | `/v2/store/inventory`      | Return pet inventory by status  |
| `POST` | `/v2/store/order`          | Place an order for a pet        |
| `GET`  | `/v2/store/order/{orderId}`| Find purchase order by ID       |
| `DELETE`| `/v2/store/order/{orderId}`| Delete purchase order by ID    |

### User — `/v2/user`

| Method | Path                         | Description                     |
|--------|------------------------------|---------------------------------|
| `GET`  | `/v2/user`                   | Return all users                |
| `POST` | `/v2/user`                   | Create user                     |
| `POST` | `/v2/user/createWithArray`   | Create list of users (array)    |
| `POST` | `/v2/user/createWithList`    | Create list of users (list)     |
| `GET`  | `/v2/user/login`             | Log user into the system *(501)*|
| `GET`  | `/v2/user/logout`            | Log out current user *(501)*    |
| `GET`  | `/v2/user/{username}`        | Get user by username            |
| `PUT`  | `/v2/user/{username}`        | Update user                     |
| `DELETE`| `/v2/user/{username}`       | Delete user                     |

> *(501)* — endpoint is scaffolded and returns `501 Not Implemented`.

### Actuator

| Path                   | Description         |
|------------------------|---------------------|
| `/actuator/health`     | Liveness / readiness|
| `/actuator/info`       | Application info    |
| `/actuator/metrics`    | Micrometer metrics  |

---

## API Documentation (Swagger UI)

Once the server is running, open:

- **Swagger UI** → <http://localhost:8080/swagger-ui.html>
- **OpenAPI JSON** → <http://localhost:8080/v3/api-docs>

---

## Running Tests

```bash
# Run all unit tests
mvn test

# Run tests and generate a Surefire report
mvn verify
```

Tests are located in `src/test/java/net/petstore/` and cover:

- `PetApiControllerTest` — controller-layer slice tests for the Pet API
- `UserApiControllerTest` — controller-layer slice tests for the User API
- `PetServiceTest` — unit tests for pet business logic
- `UserServiceTest` — unit tests for user business logic

---

## Docker Build

The project uses a **multi-stage Dockerfile**:

1. **Builder** — `maven:3.9-eclipse-temurin-21-alpine` compiles and packages the JAR.
2. **Runtime** — `eclipse-temurin:21-jre-alpine` runs the packaged JAR as an unprivileged user (`app`).

A health check polls `GET /actuator/health` every 30 seconds.

```bash
# Standard build (tests skipped)
docker build -t spring-server-petstore .

# Build with tests
docker build --build-arg MAVEN_ARGS="" -t spring-server-petstore .

# Force a cache-bust (useful for SNAPSHOT builds)
docker build --build-arg CACHEBUST=$(date +%s) -t spring-server-petstore .
```

---

## CI/CD

The GitHub Actions workflow (`.github/workflows/docker-publish.yml`) runs on every push / PR to `main`:

1. Checks out the repository.
2. Sets up QEMU + Docker Buildx for **multi-arch** builds (`linux/amd64`, `linux/arm64`).
3. Logs into Docker Hub using `DOCKERHUB_USERNAME` / `DOCKERHUB_PASSWORD` repository secrets.
4. Builds and pushes the image as `<user>/spring-mongo-server-petstore:mongo`.
5. For SNAPSHOT versions detected in `pom.xml`, a cache-bust argument is injected automatically.

> Pull requests perform a build-only run (no push).

---

## Project Structure

```
src/
├── main/
│   ├── java/net/petstore/
│   │   ├── api/              # REST controllers (Pet, Store, User)
│   │   ├── configuration/    # Jackson, OpenAPI, HomeController
│   │   ├── domain/           # Redis hash entities
│   │   ├── mapper/           # MapStruct mappers (domain ↔ model)
│   │   ├── model/            # API model DTOs
│   │   ├── repository/       # Spring Data Redis repositories
│   │   ├── security/         # JWT filter, Spring Security config
│   │   └── service/          # Business logic (PetService, UserService)
│   └── resources/
│       ├── application.yaml          # Default configuration
│       └── application-local.yaml    # Local overrides
└── test/
    └── java/net/petstore/
        ├── api/              # Controller tests
        └── service/          # Service tests
```

---

## License

This project is licensed under the terms of the [LICENSE](LICENSE) file included in the repository.
