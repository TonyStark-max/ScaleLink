# ScaleLink – Distributed URL Shortener

ScaleLink is a scalable backend URL shortener built with **Spring Boot**.
The system is designed to demonstrate **real backend engineering concepts** such as caching, rate limiting, asynchronous event processing, and distributed architecture patterns.

The project focuses on building a **high-performance redirect service** capable of handling large read traffic while maintaining system reliability and scalability.

---

## 🚀 Core Features

### URL Shortening

* Create short URLs from long URLs
* Base62 encoding used for generating short codes
* Redirect users using the short code

### Redis Caching

* Implements **cache-aside pattern**
* Redirect requests are served from Redis when available
* Reduces database load for high read traffic

### Rate Limiting

* Redis-based request limiter
* Prevents abuse of the shortening API
* Protects system resources

### Asynchronous Click Tracking

* Click events are published to **RabbitMQ**
* Background worker processes events
* Database updates happen asynchronously

### Global Exception Handling

* Centralized error handling using `@RestControllerAdvice`
* Consistent API error responses

### Input Validation

* DTO validation using Jakarta Validation
* Prevents invalid URLs or malformed requests

---

## 🧠 System Architecture

```
Client
   │
   ▼
Spring Boot API
   │
   ├── Redis (Cache + Rate Limiter)
   │
   ├── RabbitMQ (Click Events)
   │       │
   │       └── Background Worker
   │
   └── PostgreSQL (Persistent Storage)
```

### Request Flow (Redirect)

1. User requests `/api/{shortCode}`
2. System checks Redis cache
3. If cache miss → query PostgreSQL
4. URL is cached in Redis
5. Redirect user to original URL
6. Click event sent to RabbitMQ
7. Worker updates analytics asynchronously

---

## 🛠️ Tech Stack

Backend

* Java
* Spring Boot
* Spring Data JPA

Infrastructure

* Redis
* RabbitMQ
* PostgreSQL

DevOps

* Docker
* Docker Compose (planned)

---

## 📦 Project Structure

```
src/main/java/com/ScaleLink
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── config
 ├── messaging
 └── exception
```

---

## ⚙️ Running Locally

### 1. Start Dependencies

Run Redis, RabbitMQ, and PostgreSQL.

Example:

```
redis-server
rabbitmq-server
```

### 2. Configure Environment

`application.yml`

```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/scalelink
    username: postgres
    password: password

  redis:
    host: localhost
    port: 6379

  rabbitmq:
    host: localhost
```

### 3. Run Application

```
mvn spring-boot:run
```

---

## 📡 API Endpoints

### Create Short URL

```
POST /api/shorten
```

Body

```
{
  "originalUrl": "https://example.com"
}
```

Response

```
{
  "shortCode": "abc123"
}
```

---

### Redirect

```
GET /api/{shortCode}
```

Redirects to the original URL.

---

### Analytics

```
GET /api/analytics/{shortCode}
```

Returns click statistics.

---

## 📊 Performance Strategies

### Cache-Aside Pattern

Redis is used to reduce database reads for frequently accessed URLs.

### Event Driven Processing

RabbitMQ decouples click analytics from redirect requests.

### Rate Limiting

Redis protects the API from abuse and traffic spikes.

---

## 🧩 Planned Improvements

The following features are planned to make the system more production ready:

* Token bucket rate limiting
* Custom alias support
* URL expiration
* Dead letter queue for failed events
* Distributed ID generator (Snowflake)
* Redis hot-key mitigation
* Observability using Spring Boot Actuator
* Containerized deployment with Docker
* CI/CD pipeline using GitHub Actions
* Cloud deployment (AWS / GCP)

---

## 🐳 Containerization (Next Step)

The project will be containerized using Docker.

Services:

```
API
Redis
RabbitMQ
PostgreSQL
```

All services will be orchestrated using **Docker Compose**.

---

## 🎯 Learning Goals

This project demonstrates:

* Backend service architecture
* Distributed systems patterns
* Event-driven design
* Caching strategies
* Message queues
* Rate limiting
* Scalable API design

---

## 📜 License

MIT License
