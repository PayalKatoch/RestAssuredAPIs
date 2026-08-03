# REST Assured API Automation Framework

![CI](https://github.com/PayalKatoch/RestAssuredAPIs/actions/workflows/ci.yml/badge.svg)

A scalable, production-ready API test automation framework built with **REST Assured**, **TestNG**, and **Java 21**.

## Tech Stack

- **Java 21** — Language
- **REST Assured** — API testing library
- **TestNG** — Test runner with parallel execution
- **Lombok** — Reduces boilerplate in POJOs
- **DataFaker** — Dynamic test data generation
- **Allure** — Rich HTML test reports with request/response capture
- **Maven** — Build and dependency management
- **Docker** — Containerized test execution
- **Jenkins** — CI/CD pipeline with environment selection

## Project Structure

```
src/test/
├── java/
│   ├── api/                    # API layer
│   │   ├── EcommerceSpecBuilder.java   # Ecommerce request/response specs
│   │   ├── SpotifyAPI.java             # Spotify playlist CRUD operations
│   │   └── Route.java                  # Centralized API endpoint constants
│   ├── pojo/                   # Data models (Lombok)
│   │   ├── Playlist.java, OrderDetail.java, Orders.java
│   │   ├── LoginRequest.java, LoginResponse.java
│   │   ├── AddPlace.java, Location.java
│   │   └── GetCourses.java, Api.java, Courses.java ...
│   ├── payload/                # Static JSON payloads
│   │   └── RequestPayload.java
│   ├── test/                   # TestNG test classes
│   │   ├── BaseTest.java               # Global setup (Allure filter)
│   │   ├── EcommerceAPITest.java       # E2E: add product → order → delete
│   │   ├── SpotifyPlaylistTest.java    # E2E: create → get → update → verify
│   │   ├── GoogleMapAPITest.java       # Add, update, get place
│   │   ├── OAuthTest.java             # OAuth2 token + course details
│   │   ├── SerializationTest.java     # Java object → JSON serialization
│   │   ├── DynamicJsonTest.java       # Data-driven tests with DataProvider
│   │   ├── ComplexJsonParseTest.java   # Complex JSON parsing & validation
│   │   └── SumValidationLogicTest.java # Business logic validation
│   └── utils/                  # Utilities
│       ├── ConfigLoader.java          # Environment-based config loader
│       ├── TokenManager.java          # Thread-safe token management
│       ├── PropertyUtils.java         # Properties file reader
│       ├── RetryAnalyzer.java         # Auto-retry failed tests (2 retries)
│       └── RetryListener.java         # Global retry listener
├── resources/
│   ├── config-qa.properties           # QA environment config
│   ├── config-staging.properties      # Staging environment config
│   ├── config-prod.properties         # Production environment config
│   └── product_image.jpeg             # Test image for product upload
├── testng.xml                          # Test suite with parallel execution
├── Dockerfile                          # Docker containerization
├── Jenkinsfile                         # CI/CD pipeline
└── pom.xml                             # Maven dependencies
```

## Key Features

- **Multi-API Coverage** — Ecommerce, Spotify, Google Maps, OAuth2, Library APIs
- **Environment Switching** — Run against QA, staging, or prod via `-Denv` flag
- **Parallel Execution** — TestNG `parallel="classes"` with configurable thread count
- **Thread-Safe Tokens** — Synchronized token management with auto-refresh and expiry handling
- **Dynamic Test Data** — DataFaker generates unique data per run
- **Allure Reporting** — Auto-captures HTTP request/response in reports
- **Retry Mechanism** — Auto-retries failed tests up to 2 times
- **Route Constants** — All API endpoints centralized in `Route.java`
- **Docker Ready** — Containerized execution for CI/CD
- **Lombok POJOs** — Clean, minimal data models

## Getting Started

### Prerequisites

- Java 21
- Maven 3.9+
- Allure CLI (`brew install allure`)

### Run Tests

```bash
# Run on QA environment (default)
mvn clean test

# Run on specific environment
mvn clean test -Denv=qa
mvn clean test -Denv=staging
mvn clean test -Denv=prod

# Run a specific test class
mvn test -Dtest=test.SpotifyPlaylistTest

# Generate Allure results locally
mvn clean test -Denv=qa
allure serve target/allure-results
```

### Generate Allure HTML report locally

```bash
mvn clean test -Denv=qa
allure generate target/allure-results -o target/allure-report --clean
allure open target/allure-report
```

### Run with Docker

```bash
docker build -t api-automation .
docker run --rm api-automation mvn clean test -Denv=qa
```

### Jenkins Pipeline

The `Jenkinsfile` provides:
- Environment selection dropdown (QA / Staging / Prod)
- Docker-based test execution
- Allure report publishing
- JUnit test trend tracking

## Spotify API Setup

To run Spotify playlist tests, you need a one-time setup:

1. Create an app on [Spotify Developer Dashboard](https://developer.spotify.com/dashboard)
2. Add `http://127.0.0.1:3000` as a redirect URI
3. Add your Spotify email under **User Management**
4. Run `SpotifyAuthHelper` to get your refresh token
5. Add credentials to `config-qa.properties`

## Configuration

All environment-specific values are in `config-{env}.properties`:

| Property | Description |
|----------|-------------|
| `baseUrl` | Base URL for Ecommerce/Maps APIs |
| `spotifyBaseUrl` | Spotify API base URL |
| `user_Email` / `password` | Ecommerce login credentials |
| `spotify_client_id` / `spotify_client_secret` | Spotify OAuth credentials |
| `spotify_refresh_token` | Spotify refresh token |
