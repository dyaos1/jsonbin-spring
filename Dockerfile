FROM openjdk:17-jdk-slim

# Work directory inside the container
WORKDIR /app

# Copy the application JAR file (adjust the JAR name)
COPY build/libs/jsonbin-0.0.1-SNAPSHOT.jar app.jar

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
