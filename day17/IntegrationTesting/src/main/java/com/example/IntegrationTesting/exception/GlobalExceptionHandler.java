package com.example.IntegrationTesting.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${app.debug-errors:false}")
    private boolean debugErrors;

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) {

        // ✅ Always log actual error in console (must for debugging tests)
        ex.printStackTrace();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Something went wrong");

        // ✅ If debug mode ON → send real reason also
        if (debugErrors) {
            body.put("exception", ex.getClass().getName());
            body.put("message", ex.getMessage());
        }


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
