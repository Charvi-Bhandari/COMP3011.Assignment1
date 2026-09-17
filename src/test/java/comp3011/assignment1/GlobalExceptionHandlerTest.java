package comp3011.assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import comp3011.assignment1.exception.GlobalExceptionHandler;
import comp3011.assignment1.response.ErrorResponse;

class GlobalExceptionHandlerTest {

    @Test
    void unexpectedExceptionReturnsInternalServerError() {

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        MockHttpServletRequest request = new MockHttpServletRequest(
                "GET",
                "/api/test"
        );

        ResponseEntity<ErrorResponse> response =
                handler.handleException(
                        new RuntimeException("Test exception"),
                        new ServletWebRequest(request)
                );

        assertEquals(500, response.getStatusCode().value());

        ErrorResponse errorResponse = response.getBody();

        assertEquals(500, errorResponse.getStatus());
        assertEquals("Internal Server Error", errorResponse.getError());
        assertEquals(
                "An unexpected server error occurred.",
                errorResponse.getMessage()
        );
        assertEquals("/api/test", errorResponse.getPath());
    }
}