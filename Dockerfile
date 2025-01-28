FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN ./mvnw package -DskipTests

CMD ["java", "-jar", "target/login-0.0.1-SNAPSHOT.jar"]
