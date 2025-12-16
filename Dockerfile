# ---- Build stage ----
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy only what's needed for dependency caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline

# Copy source and build
COPY src src
RUN ./mvnw clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /

COPY --from=build /app/target/*.jar /app.jar
COPY springboot.p12 /springboot.p12

ENTRYPOINT ["java","-jar","/app.jar"]
