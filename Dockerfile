# Step 1: Use Maven to build the project
FROM maven:latest AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to the container (this contains the dependencies)
COPY pom.xml .

# Download dependencies (this will save time when rebuilding)
RUN mvn dependency:go-offline

# Copy the entire src directory into the container
COPY src /app/src

# Build the application and skip tests for now
RUN mvn clean package -DskipTests

# Step 2: Use OpenJDK 21 for running the application
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build container into the current container
COPY --from=build /app/target/login-0.0.1-SNAPSHOT.jar /app/login-0.0.1-SNAPSHOT.jar

# Expose port 8080 (default Spring Boot port)
EXPOSE 8080

# Define the command to run your app
CMD ["java", "-jar", "login-0.0.1-SNAPSHOT.jar"]
