package org.exanple.moviesevice.exceptions;

import org.example.moviservice.exceptions.ErrorResponse;
import org.example.moviservice.exceptions.ExceptionsHandler;
import org.junit.jupiter.api.Test;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExceptionsHandlerTest {

    @Test
    void handleInternalServerError() {
        ExceptionsHandler handler = new ExceptionsHandler();
        Exception ex = new Exception("Test Internal Server Error");
        ErrorResponse response = handler.handleInternalServerError(ex);

        assertNotNull(response);
        assertEquals("Internal Server Error", response.getMessage());
        assertEquals(ex.getClass().getSimpleName(), response.getErrorType());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleBadRequestException() {
        ExceptionsHandler handler = new ExceptionsHandler();
        Exception ex = new HttpMessageNotReadableException("Test Bad Request");
        ErrorResponse response = handler.handleBadRequestException(ex);

        assertNotNull(response);
        assertEquals("Bad Request", response.getMessage());
        assertEquals(ex.getClass().getSimpleName(), response.getErrorType());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleMethodNotAllowed() {
        ExceptionsHandler handler = new ExceptionsHandler();
        Exception ex = new HttpRequestMethodNotSupportedException("GET");
        ErrorResponse response = handler.handleMethodNotAllowed(ex);

        assertNotNull(response);
        assertEquals("Method Not Allowed", response.getMessage());
        assertEquals(ex.getClass().getSimpleName(), response.getErrorType());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleNotFoundException() {
        ExceptionsHandler handler = new ExceptionsHandler();
        Exception ex = new NoHandlerFoundException("GET", "/test", null);
        ErrorResponse response = handler.handleNotFoundException(ex);

        assertNotNull(response);
        assertEquals("Not Found", response.getMessage());
        assertEquals(ex.getClass().getSimpleName(), response.getErrorType());
        assertNotNull(response.getTimestamp());
    }
}