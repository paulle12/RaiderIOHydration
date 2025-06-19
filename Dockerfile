# Use a base image with Maven and Java 21 to build the app
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy all project files into the image
COPY . .

# Build the application (skip tests for speed)
RUN ./mvnw clean package -DskipTests

# Expose the port (optional, good practice)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/RaiderIOHydration-0.0.1-SNAPSHOT.jar"]