# E-Commerce Microservices System (ProductStoreApp)

## System Overview
This architecture implements a modern microservices-based e-commerce system with the following components:

- **API Gateway**: Centralized entry point for all client requests
- **OAuth2/Keycloak**: Handles authentication and authorization
- **Product Service**: Manages product catalog and information
- **Inventory Service**: Tracks product stock and availability
- **Order Service**: Processes customer orders
- **Notification Service**: Handles customer notifications
- **Kafka**: Event streaming platform for asynchronous communication

## Technology Stack
- **Spring Boot**: Framework for building microservices
- **Spring Cloud**: Provides tools for building distributed systems
- **Keycloak**: Open source identity and access management
- **Kafka**: Event streaming platform for asynchronous communication
- **Docker & Docker Compose**: Containerization and orchestration
- **Databases**: Each service has its own database (SQL/NoSQL)
- **Kubernetes**: Services are deployed on Kubernetes

## Running with Keycloak
1. Ensure creation of `myrealm` (springms-realm).
2. Create a client (client-credential grant type).
3. Use the client-id and client-secret to generate a token to be authorized to use the resource-server.

## API Endpoints

### Product Service
- **Endpoint**: `http://products:8084/api/products`
- **ProductDto**:
  ```json
  {
    "productId": "1L",
    "name": "name4",
    "price": 4.0,
    "category": "category4",
    "description": "description4"
  }

### Inventory Service
- **Endpoint**: `http://products:8085/api/Inventory`
- **InventoryDto**:
  ```json
  {
    "id": 1,
    "productId": "1L",
    "quantity": 100
  }

### Order Service
- **Endpoint**: `http://products:8086/api/order`
- **OrderDto**:
  ```json
  {
    "orderId": 1,
    "productId": "1L",
    "quantity": 2,
    "userEmail": "user@example.com",
    "status": "Pending",
    "orderDate": "2025-03-11T19:54:21",
    "productDto": {
      "productId": "1L",
      "name": "name4",
      "price": 4.0,
      "category": "category4",
      "description": "description4"
    }
  }

## Microservice Architecture
