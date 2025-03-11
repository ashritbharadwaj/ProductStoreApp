#!/bin/bash

# List of all services
services=("order-service" "product-service" "inventory-service" "notify-service" "gateway-service")

# Loop over each service and build the image
for service in "${services[@]}"
do
  echo "Building JAR file for $service..."

  # Navigate to the service's directory
  cd $service

  # Build the JAR file using Maven (adjust command if using a different profile or settings)
  mvn clean package -DskipTests  # Skip tests to speed up the build, remove if you want to run tests

  if [ $? -ne 0 ]; then
    echo "Maven build failed for $service, skipping Docker image build."
    cd ..  # Go back to the root directory
    continue  # Skip to the next service
  fi

  echo "Building Docker image for $service..."

  # Build the Docker image
  docker build -t pes1ug21cs120ashritbharadwaj/$service:latest .

  echo "Pushing Docker image to Docker Hub for $service..."

  # Push the image to Docker Hub
  docker push pes1ug21cs120ashritbharadwaj/$service:latest

  # Go back to the root directory
  cd ..
done

echo "All services built successfully!"
