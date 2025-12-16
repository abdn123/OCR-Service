FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app.jar
COPY springboot.p12 /springboot.p12
ENTRYPOINT ["java","-jar","/app.jar"]