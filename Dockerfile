FROM openjdk:21-slim
WORKDIR /app
COPY JenkinsCICD/target/*.jar app.jar
EXPOSE 7779
ENTRYPOINT ["java", "-jar", "app.jar"]
