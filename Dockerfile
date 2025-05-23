FROM openjdk:21
EXPOSE 8080
ADD target/eyf.jar /eyf.jar
ENTRYPOINT ["java", "-jar", "/eyf.jar"]
