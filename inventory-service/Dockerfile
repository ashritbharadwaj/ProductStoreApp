# Use a base image that includes Java 21 (OpenJDK)
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the target directory into the container's /app directory
COPY target/inventory-service.jar app.jar

# Expose the port your Spring Boot application runs on (default is 8080)
EXPOSE 8085

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]