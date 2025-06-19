# Base image with Java 21
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR into the container
COPY target/RaiderIOHydration-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on (can keep 8080 internally)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]