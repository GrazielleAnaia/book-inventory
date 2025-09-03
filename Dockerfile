FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests


FROM amazoncorretto:17-alpine3.19-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar ./
EXPOSE 8080
CMD ["java", "-jar", "/app/book-inventory.jar"]
