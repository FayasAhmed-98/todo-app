# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy only the POM file first for dependency caching
COPY pom.xml ./
RUN mvn dependency:resolve

# Copy the rest of the project files
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the built JAR using a lightweight JDK image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
