# Use an official OpenJDK runtime as a parent image
FROM adoptopenjdk:17-jre-hotspot

# Set the working directory in the container
WORKDIR /order-management-app

# Copy the application JAR file into the container at /app
COPY target/backend-1.0.jar /app

# Specify the command to run on container start
CMD ["java", "-jar", "backend-1.0.jar"]