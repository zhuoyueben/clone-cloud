FROM openjdk:8
ARG JAR_FILE
#WORKDIR /ROOT
COPY ${JAR_FILE} app.jar
EXPOSE 8885
#ENTRYPOINT ["java","-jar","${project.build.finalName}.jar"]
ENTRYPOINT ["java","-jar","app.jar"]