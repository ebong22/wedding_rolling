FROM --platform=linux/amd64 openjdk:17
ARG JAR_FILE=./build/libs/wedding-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]