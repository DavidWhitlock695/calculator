# To run the application in a containerised environment

1. Make the Dockerfile (should already be done - running of a small distroless java17 image as base layer)
2. Build the .jar file (in this case, just run "mvn clean package" to refresh jar file)

Note: "mvn clean install" would be similar, but it also makes the package available in your local mvn repo.

3. Run "docker build -t calculator ."

This builds the image, downloading any required base images if necessary.
It tags the resulting image with the name returns-manager. You could add a version e.g.
"returns-manager:1.0"

4. Run "docker run -p 8080:8080 calculator:latest"

To run the image in a container at port 8080 (both as seen from within and without)

5. Run "docker ps" to check the image is running, get its container ID and go to localhost:8080/health
6. Run "docker stop <container-id>" to stop the image
7. Run "docker logs <container-id>" if you'd like to see the image's logs
