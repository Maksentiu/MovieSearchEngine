package org.exanple.moviesevice.exceptions;

import org.example.moviservice.exceptions.ErrorResponse;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ErrorResponseTest {

    @Test
    void constructorAndGetters() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "Test message";
        String errorType = "Test error";

        // Act
        ErrorResponse errorResponse = new ErrorResponse(timestamp, message, errorType);

        // Assert
        assertNotNull(errorResponse);
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(errorType, errorResponse.getErrorType());
    }

    @Test
    void setters() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse();
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "Test message";
        String errorType = "Test error";

        // Act
        errorResponse.setTimestamp(timestamp);
        errorResponse.setMessage(message);
        errorResponse.setErrorType(errorType);

        // Assert
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(errorType, errorResponse.getErrorType());
    }
}
