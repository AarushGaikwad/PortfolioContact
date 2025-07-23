# First step: use Maven to build the JAR
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app
COPY . .    
RUN mvn clean package -DskipTests

# Second step: use a small Java image to run the app
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/portfolioContact-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]
