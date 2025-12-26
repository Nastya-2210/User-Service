FROM openjdk:21-jdk-slim
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /app

COPY target/*.jar app.jar
COPY wait-for-it.sh /app/wait-for-it.sh

RUN chmod +x /app/wait-for-it.sh

USER spring

EXPOSE 8081

ENTRYPOINT ["/app/wait-for-it.sh", "config-server:8889", "--", "/app/wait-for-it.sh", "kafka:9092", "--", "/app/wait-for-it.sh", "eureka-server:8761", "--", "/app/wait-for-it.sh", "mailhog:1025", "--", "java", "-Deureka.client.serviceUrl.defaultZone=http://eureka-server:8761/eureka", "-jar", "/app/app.jar"]