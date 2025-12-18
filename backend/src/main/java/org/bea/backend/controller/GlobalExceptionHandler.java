package org.bea.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        log.error("Unhandled exception caught", ex);
        Map<String, Object> body = Map.of(
                "error", "Error: " + ex.getMessage(),
                "timestamp", Instant.now().toString(),
                "status", "INTERNAL_SERVER_ERROR"
        );
        return ResponseEntity.status(500).body(body);
    }

}
