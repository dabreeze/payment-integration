FROM eclipse-temurin:20-jre-alpine as builder
WORKDIR extracted
ADD ./target/paystack_integration-0.0.1-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:20-jre-alpine
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
RUN true
COPY --from=builder extracted/spring-boot-loader/ ./
RUN true
COPY --from=builder extracted/snapshot-dependencies/ ./
RUN true
COPY --from=builder extracted/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.jarLauncher"]