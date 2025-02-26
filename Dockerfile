FROM amazoncorretto:17
WORKDIR /app
COPY build/libs/*.jar  /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
