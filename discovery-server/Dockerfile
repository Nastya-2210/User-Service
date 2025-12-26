FROM openjdk:21-jdk-slim

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

RUN groupadd -r spring && useradd -r -g spring spring
USER spring

WORKDIR /app

COPY target/*.jar app.jar


EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app/app.jar"]