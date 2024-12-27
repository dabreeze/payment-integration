# Stage 1: Builder (for building the project using Maven)
FROM eclipse-temurin:21-jre-alpine as builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the source code to the container
COPY pom.xml .
COPY src ./src

# Run Maven build to create the target directory with the JAR file
RUN mvn clean package

# Stage 2: Final image (for running the application)
FROM eclipse-temurin:21-jre-alpine

# Set the working directory for the final image
WORKDIR /application

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/paystack_integration-0.0.1-SNAPSHOT.jar app.jar

# Set the entrypoint to run the application JAR file
ENTRYPOINT ["java", "-jar", "/application/app.jar"]
