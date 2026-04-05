FROM openjdk:21-jdk-slim
WORKDIR /app
COPY JenkinsCICD/target/*.jar app.jar
EXPOSE 7778
ENTRYPOINT ["java", "-jar", "app.jar"]
