# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/school-0.0.1-SNAPSHOT.jar ./school.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "school.jar"]