FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY JenkinsCICD/target/*.jar app.jar
EXPOSE 7778
ENTRYPOINT ["java", "-jar", "app.jar"]
