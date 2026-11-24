FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY src src
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml pom.xml
RUN chmod +x mvnw
RUN ./mvnw package -DskipTests
ENTRYPOINT ["java", "-jar", "target/tracking-0.0.1-SNAPSHOT.jar"]
