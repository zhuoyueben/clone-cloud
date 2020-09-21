FROM openjdk:8
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8885
ENTRYPOINT ["java","-jar","/app.jar"]