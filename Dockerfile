# Base OS image
FROM gcr.io/distroless/java17-debian12
# Alpine's package manager will use its open-JRE for Java 17 (JRE is just the runtime, not the development env - more lightweight since already compiled).
# RUN apk add --no-cache openjdk17-jre
# When we built the JAR file, it saved it here... we're saving it simply as app.jar in the image. 
COPY target/calculator-0.0.1-SNAPSHOT.jar app.jar
# Once copied over into the container, run the command "java -jar /app.jar"
ENTRYPOINT ["java", "-jar", "/app.jar"]

