# Stage 1: Builder (for building the project using Maven)
FROM maven:3-eclipse-temurin-21 as builder

WORKDIR /app

# Copy the pom.xml and the source code to the container
COPY pom.xml .
COPY src ./src

# Run Maven build to create the target directory with the JAR file
RUN mvn clean install

# Stage 2: Extract layers from the Spring Boot JAR
FROM eclipse-temurin:21-jre-alpine as extractor

WORKDIR /extracted

# Copy the JAR file from the builder stage into the extractor stage
COPY --from=builder /app/target/paystack_integration-0.0.1-SNAPSHOT.jar app.jar

# Extract layers using Spring Boot Layertools
RUN java -Djarmode=layertools -jar app.jar extract

# Debugging: List the extracted files to verify if they were extracted correctly
RUN ls -alh /extracted

# Stage 3: Final image (for running the application)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /application

# Copy the extracted layers from the extractor stage into the final image
COPY --from=extractor /extracted/application/ /application/
COPY --from=extractor /extracted/dependencies/ /application/dependencies/
COPY --from=extractor /extracted/spring-boot-loader/ /application/spring-boot-loader/
COPY --from=extractor /extracted/snapshot-dependencies/ /application/snapshot-dependencies/

# Optional: Debugging step to check if files were copied correctly
RUN ls -alh /application
RUN ls -alh /application/spring-boot-loader/org/springframework/boot/jar

# Set the entrypoint to use the spring-boot-loader.jar
ENTRYPOINT ["java", "-jar", "/application/spring-boot-loader/org/springframework/boot/jar/spring-boot-loader.jar"]
