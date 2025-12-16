FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src src
RUN ./mvnw clean package -DskipTests

COPY target/*.jar /app.jar
COPY springboot.p12 /springboot.p12

ENTRYPOINT ["java","-jar","/app.jar"]
