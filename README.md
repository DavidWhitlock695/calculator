# To run the application in a containerised environment

1. Make the Dockerfile (already done - running of a small distroless java17 image as base layer)
2. Build the .jar file:
        - Run "mvn clean package" in the root directory of the project
        - Without tests: "mvn clean package -DskipTests"

Note: "mvn clean install" would be similar, but it also makes the package available in your local mvn repo.

----------------------------------------------------------------------------------------
## Building and running just the app container

3. If running just the container for the app, run "docker build -t calculator ."

This builds the image, downloading any required base images if necessary.
It tags the resulting image with the name returns-manager. You could add a version e.g.
"returns-manager:1.0"

4. Run "docker run -p 8080:8080 calculator:latest"

To run the image in a container at port 8080 (both as seen from within and without)

5. Run "docker ps" to check the image is running, get its container ID and go to localhost:8080/health
6. Run "docker stop <container-id>" to stop the image
7. Run "docker logs <container-id>" if you'd like to see the image's logs

8. To Curl requests with a body use the following template as an example:

```
curl -X POST http://localhost:8080/calculator/binaryOperation \
   -H "Content-Type: application/json" \
   -d '{"operandOne": 0.1, "operandTwo": 0.2, "operator":"ADD"}'
```
-------------------------------------------------------------------------------------------
## Building and running with Docker Compose

1. Build the image as before using ./mvnw clean package (or with skip tests)
2. Run "docker-compose up --build" to build and run the image
3. To view logs for the app and db use:
   - "docker-compose logs -f app" or
   - "docker-compose logs -f db"
4. To stop the containers use "docker-compose down"
-------------------------------------------------------------------------------------------
# Notes

The testing profile uses an in-memory H2 database, so no external DB is required for tests.
This is defined in src/main/resources/application-test.properties
The main application uses a Postgres DB, which can be run in a container using the provided
docker-compose.yml file.
