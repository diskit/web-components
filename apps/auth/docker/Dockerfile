FROM maven:3.8.1-openjdk-15-slim as build
WORKDIR /work
COPY app app
RUN cd app && mvn clean package

FROM openjdk:15-jdk-slim
WORKDIR /work
COPY --from=build /work/app/target/auth-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
