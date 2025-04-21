FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar

# Exposing port 8080
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]