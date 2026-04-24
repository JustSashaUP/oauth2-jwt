#Image
FROM maven:3.9-eclipse-temurin-25 as build
#Set directory in the container
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
#Build app using Maven
RUN mvn package -DskipTests

#OpenJDK image
FROM eclipse-temurin:25-jre
WORKDIR /app

#Copy built JAR file to the container
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]