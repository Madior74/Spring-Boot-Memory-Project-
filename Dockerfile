# Étape 1 : Build
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Run
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/Systeme-de-Gestion-Scolaire-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9000
CMD ["java", "-jar", "app.jar"]
