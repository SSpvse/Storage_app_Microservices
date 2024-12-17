FROM amazoncorretto:21
COPY ./target/loginservice-0.0.1-SNAPSHOT.jar /usr/src/loginservice/
WORKDIR /usr/src/loginservice
EXPOSE 8085
CMD ["java", "-jar", "loginservice-0.0.1-SNAPSHOT.jar"]

# Building the Dockerfile
# docker build -t itemservice-0.0.1-SNAPSHOT .