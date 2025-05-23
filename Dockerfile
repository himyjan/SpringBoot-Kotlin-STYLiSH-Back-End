FROM amazoncorretto:23-alpine-jdk

# create a directory for the app
WORKDIR /app

#Copy everything from the current directory to the app directory
COPY . .

# Build the app
RUN ./gradlew clean build -x test

# Expose port 8080
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "./build/libs/backend-0.0.1-SNAPSHOT.jar"]