

#FROM maven:3-eclipse-temurin-21 as build
#COPY . .
#RUN mvn clean package -Dskiptests
#FROM oraclelinux:8-slim
#COPY --from=build /target/paystack_integration-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM maven:3-eclipse-temurin-21 as build
#FROM eclipse-temurin-21-jammy as build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -Dskiptests

FROM 3.9-eclipse-temurin-21-alpine

WORKDIR /app
COPY --from=build /app/target/paystack_integration-0.0.1-SNAPSHOT.jar .

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/paystack_integration-0.0.1-SNAPSHOT.jar"]