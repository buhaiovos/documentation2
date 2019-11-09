FROM openjdk:13
WORKDIR /usr/src/backend
COPY ./target/Documentation-0.1.jar /usr/src/backend
ENV DB_URL jdbc:mysql://localhost:3306/cad_database_2016?characterEncoding=utf8&serverTimezone=UTC&useSSL=false
ENV DB_USER_NAME caduser
ENV DB_PASSWORD cadcat$
EXPOSE 8080
CMD java -jar Documentation-0.1.jar
