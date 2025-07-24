# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR from target folder
COPY target/eventdemo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app listens on
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
