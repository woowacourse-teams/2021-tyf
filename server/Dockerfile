FROM azul/zulu-openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT["java", "-jar", "-Dspring.profiles.active=dev", "/app.jar"]
