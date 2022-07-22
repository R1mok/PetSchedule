FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} pet-schedule.jar
ENTRYPOINT ["java","-jar","pet-schedule.jar"]