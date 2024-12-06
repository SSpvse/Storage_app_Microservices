FROM amazoncorretto:21
COPY ./target/unitservice-0.0.1-SNAPSHOT.jar /usr/src/unitService/
WORKDIR /usr/src/unitService
EXPOSE 8081
CMD ["java", "-jar", "unitservice-0.0.1-SNAPSHOT.jar"]