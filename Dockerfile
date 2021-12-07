FROM openjdk:8-alpine
add build/libs/rest-user-store-0.0.1.jar rest-user-store.jar
ENTRYPOINT ["java","-jar","rest-user-store.jar"]