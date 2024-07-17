FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
COPY src/main/resources/keystore.p12 /app/keystore.p12
ENTRYPOINT ["java","-jar","/app.jar"]
