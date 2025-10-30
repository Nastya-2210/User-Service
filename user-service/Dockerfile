FROM eclipse-temurin:21-jdk-alpine

RUN apk add --no-cache tini bash curl

WORKDIR /app

COPY target/user-service-0.0.1-SNAPSHOT.jar /app/app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/postgres
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=2210

ENTRYPOINT ["/app/wait-for-it.sh", "config-server:8889", "--", \
            "/app/wait-for-it.sh", "postgres:5432", "--timeout=60", "--strict", "--", \
            "/app/wait-for-it.sh", "eureka-server:8761", "--", \
            "java", "-Dspring.datasource.hikari.connection-timeout=120000", \
            "-Dspring.cloud.discovery.client.initialization-timeout-seconds=180", \
            "-Deureka.client.serviceUrl.defaultZone=http://eureka-server:8761/eureka", \
            "-jar", "/app/app.jar"]