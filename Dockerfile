FROM registry.access.redhat.com/ubi9/openjdk-17-runtime
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
EXPOSE 8080
CMD ["java","-jar","app.jar"]
