FROM amazoncorretto:21
COPY ./target/itemservice-0.0.1-SNAPSHOT.jar /usr/src/itemService/
WORKDIR /usr/src/itemService
EXPOSE 8082
CMD ["java", "-jar", "itemservice-0.0.1-SNAPSHOT.jar"]

# Building the Dockerfile
# docker build -t itemservice-0.0.1-SNAPSHOT .