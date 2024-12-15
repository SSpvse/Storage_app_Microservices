FROM amazoncorretto:21
COPY ./target/emailservice-0.0.1-SNAPSHOT.jar /usr/src/emailService/
WORKDIR /usr/src/emailService
EXPOSE 8084
CMD ["java", "-jar", "emailservice-0.0.1-SNAPSHOT.jar"]
