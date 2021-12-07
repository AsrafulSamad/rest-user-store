FROM openjdk:8-alpine
add rest/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]