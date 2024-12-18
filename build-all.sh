#!/bin/bash

# Navigate to the root directory
ROOT_DIR=$(pwd)

# Step 1: Start only the postgres service
echo "Starting postgres service..."
cd "$ROOT_DIR/Backend/Docker" || { echo "Docker directory not found. Exiting."; exit 1; }
docker-compose up --build -d postgres || { echo "Failed to start postgres. Exiting."; exit 1; }
cd "$ROOT_DIR" || exit 1

# Step 2: Perform mvn clean install for each service
services=("ItemService" "UnitService" "EmailService" "LoginService" "NotificationService" "Gateway")
for service in "${services[@]}"; do
    echo "Building $service..."
    cd "$ROOT_DIR/Backend/$service" || { echo "Failed to navigate to $service. Skipping."; continue; }
    mvn clean install || { echo "Build failed for $service. Exiting."; exit 1; }
    cd "$ROOT_DIR" || exit 1
done

# Step 3: Bring down the running services
echo "Bringing down running services..."
cd "$ROOT_DIR/Backend/Docker" || { echo "Docker directory not found. Exiting."; exit 1; }
docker-compose down || { echo "Failed to bring down services. Exiting."; exit 1; }

# Step 4: Build and start all services
echo "Starting all services with docker-compose..."
docker-compose up --build
