FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN chmod +x mvnw  # Grant execute permissions to mvnw
RUN ./mvnw package -DskipTests
CMD ["java", "-jar", "target/login-0.0.1-SNAPSHOT.jar"]
