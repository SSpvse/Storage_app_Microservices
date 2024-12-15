FROM amazoncorretto:21
COPY ./target/notificationservice-0.0.1-SNAPSHOT.jar /usr/src/notificationService/
WORKDIR /usr/src/notificationService
EXPOSE 8086
CMD ["java", "-jar", "notificationservice-0.0.1-SNAPSHOT.jar"]
