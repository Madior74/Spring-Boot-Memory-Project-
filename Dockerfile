FROM openjdk:17-alpine

WORKDIR /app

COPY target/Systeme-de-Gestion-Scolaire-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9000

CMD ["java","-jar","app.jar"]
