FROM openjdk:21
EXPOSE 8080
ADD backend/target/eyf.jar /eyf.jar
ENTRYPOINT ["java", "-jar", "/eyf.jar"]
