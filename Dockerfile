# Stage 1: Builder (for extracting layers from the Spring Boot JAR)
FROM eclipse-temurin:21-jre-alpine as builder

# Set the working directory where the extracted layers will be stored
WORKDIR /extracted

# Add the JAR file from the local target directory into the container
ADD ./target/paystack_integration-0.0.1-SNAPSHOT.jar app.jar

# Extract layers using Spring Boot Layertools
RUN java -Djarmode=layertools -jar app.jar extract

# Stage 2: Final image (for running the application)
FROM eclipse-temurin:21-jre-alpine

# Set the working directory for the final application
WORKDIR /application

# Copy the extracted layers from the builder stage
COPY --from=builder /extracted/application/ /application/
COPY --from=builder /extracted/dependencies/ /application/dependencies/
COPY --from=builder /extracted/spring-boot-loader/ /application/spring-boot-loader/
COPY --from=builder /extracted/snapshot-dependencies/ /application/snapshot-dependencies/

# Optional: Debugging step to check if files were copied correctly
RUN ls -alh /application

# Set the entrypoint to use the spring-boot-loader.jar
ENTRYPOINT ["java", "-jar", "/application/spring-boot-loader/spring-boot-loader.jar"]
