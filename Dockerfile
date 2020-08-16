FROM maven:3.6.3-jdk-14 as builder
WORKDIR /app
COPY pom.xml .
COPY backend ./backend
COPY frontend ./frontend
RUN mvn clean package

FROM adoptopenjdk/openjdk14:jdk-14.0.2_12-alpine-slim
COPY --from=builder /app/backend/target/backend-0.1.jar /backend-0.1.jar
CMD ["java", "-jar", "--enable-preview", "/backend-0.1.jar"]
