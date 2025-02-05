#build
FROM maven:3.9.9-amazoncorretto-23-al2023 as BUILD
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

# run
FROM amazoncorretto:23-jdk
WORKDIR /app
COPY --from=build ./build/target/*.jar ./app.jar

ENTRYPOINT java -jar app.jar
