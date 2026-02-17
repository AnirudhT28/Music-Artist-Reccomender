# Step 1: Build the Java app
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the Java app
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built JAR from the first step
COPY --from=build /app/target/*.jar app.jar

# Copy your pre-populated database into the container
COPY artist_graph.db artist_graph.db

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
