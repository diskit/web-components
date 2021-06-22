#syntax=docker/dockerfile:1.0-experimental

FROM maven:3.8.1-openjdk-15-slim as build
WORKDIR /work
COPY app/src /work/src
COPY app/pom.xml /work/pom.xml
RUN --mount=type=cache,target=/root/.m2 mvn clean package

FROM openjdk:15-jdk-slim
WORKDIR /work
COPY --from=build /work/target/auth-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
