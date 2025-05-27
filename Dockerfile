FROM maven:3.9-amazoncorretto-17-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean compile test package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
