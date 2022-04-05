FROM openjdk:8-jdk-alpine
VOLUME /temp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]