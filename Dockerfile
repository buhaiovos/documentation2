FROM maven:3.6.3-openjdk-15-slim as builder
WORKDIR /app
COPY pom.xml .
COPY backend ./backend
COPY frontend ./frontend
RUN mvn clean package

FROM adoptopenjdk/openjdk15:jre-15.0.1_9-alpine
COPY --from=builder /app/backend/target/backend-0.1.jar /backend-0.1.jar
CMD ["java", "--enable-preview", "-Duser.timezone=UTC", "--illegal-access=deny", "-jar", "/backend-0.1.jar"]
