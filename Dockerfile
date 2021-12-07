FROM openjdk:8-alpine
add app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]