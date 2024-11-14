FROM openjdk:17-jdk-slim
RUN ./mvnw clean package -DskipTests
ARG JAR_FILE=target/*.jar
COPY .env /.env
COPY ./target/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]