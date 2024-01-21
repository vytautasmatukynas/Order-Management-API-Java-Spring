# Use a base image with the desired JDK version
FROM amazoncorretto:17-alpine

# Set the working directory inside the container
WORKDIR /api

# This line defines an argument named JAR_FILE with the default value of target/*.jar
# This allows you to pass the JAR file as a build argument when building the Docker image.
ARG JAR_FILE=target/*.jar

# Copy the JAR file into the container at /app
COPY ./target/oder-management-api-spring-1.0.jar /api/order-management-api-spring-1.0.jar

# Expose the port that your Spring Boot app is running on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "order-management-api-spring-1.0.jar"]
