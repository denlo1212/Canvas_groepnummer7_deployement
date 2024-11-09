# Build stap
FROM maven:3.6.3-openjdk-11 AS builder
WORKDIR /app

COPY registrations .

RUN mvn dependency:go-offline -B \
RUN mvn clean package -DskipTests

#uber jar / shaded jar

# Production
FROM openjdk:21 AS production

COPY --from=builder /app/registrations/target/*.jar registration.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "registration.jar"]