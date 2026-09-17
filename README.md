# COMP3011 Assignment 1

Java Spring Boot speech-to-text web application.

## Running the application

Requires Java 25 and Maven.

Build the project using `Run as -> Maven Build -> goal: clean package` after right clicking the project folder.

Run the application using `java -jar target/Assignment1-0.0.1-SNAPSHOT.jar`.

The application runs at http://localhost:8080/

The OpenAI API key must be provided using the `OPENAI_API_KEY` environment variable. The key is not stored in the project or sent to the browser.

## API endpoints

POST /api/audio - Uploads recorded audio and returns the transcription.

GET /api/v1/admin/uptime - Returns the server start time, current UTC time and uptime.

GET /api/v1/global/stats - Returns the cumulative input and output token counts.

POST /api/v1/admin/shutdown - Requests a graceful server shutdown. Returns 202 when accepted and 409 if shutdown is already in progress.

## Testing

Run the tests using `mvn test` or `right click project folder -> Run As -> Maven test`.

The tests cover the controllers, error handling, concurrent requests, global token statistics and concurrent shutdown requests.

## Project structure

The frontend files are in `src/main/resources/static`.

The Java code is in `src/main/java/comp3011/assignment1`.

The application uses the OpenAI `gpt-4o-mini-transcribe` model for transcription.
