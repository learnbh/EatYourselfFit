# --- STAGE 1: Build the jar using Maven ---
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- STAGE 2: Run the application ---
FROM eclipse-temurin:21
EXPOSE 8080
# Kopiert das gebaute Jar aus Stage 1 (passt sich automatisch an den Maven-Namen an)
COPY --from=builder /app/target/*.jar /eyf.jar
ENTRYPOINT ["java", "-jar", "/eyf.jar"]