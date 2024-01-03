FROM openjdk:8-jdk-alpine
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} patientHub.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","patientHub.jar"]