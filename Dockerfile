# ======================================
# STEP 1: Build the application
# ======================================
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Copy Maven wrapper and pom.xml first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (to cache)
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the app
COPY src ./src

# Build the Spring Boot app
RUN ./mvnw clean package -DskipTests

# ======================================
# STEP 2: Run the application
# ======================================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port Render will use
EXPOSE 8080

# Run the Spring Boot JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
