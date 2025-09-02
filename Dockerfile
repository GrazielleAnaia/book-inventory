FROM maven:3.9-amazoncorretto-17-alpine as build

WORKDIR /app

COPY . .
RUN mvn clean install -DSkipTests


FROM amazoncorretto:17-alpine3.19-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/book-inventory.jar"]
