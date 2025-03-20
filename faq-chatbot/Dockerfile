# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the target directory (assuming you use Maven)
COPY target/*.jar app.jar

# Set the environment variable for the API key (Render will override this value)
ENV OPENROUTER_API_KEY="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"

# Command to run the application
CMD ["java", "-jar", "app.jar"]
