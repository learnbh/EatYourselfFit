package org.bea.backend.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleUnknownException(Exception e){
        ExceptionMessage error = new ExceptionMessage("Error: "+e.getMessage(),
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.name());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR );
    }
    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionMessage> handleIdNotFoundException(IdNotFoundException e){
        ExceptionMessage error = new ExceptionMessage(
                "Error: "+e.getMessage(),
                Instant.now(),
                HttpStatus.NOT_FOUND.name());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> messages = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            messages.put(error.getField(), error.getDefaultMessage());
        }
        ValidationErrorResponse error = new ValidationErrorResponse(
                messages,
                Instant.now(),
                HttpStatus.BAD_REQUEST.name()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionMessage> handleDuplicateKeyException() {
        ExceptionMessage error = new ExceptionMessage(
                "Error: A ingredient with this product-variation combination already exists",
                Instant.now(),
                HttpStatus.CONFLICT.name());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ExceptionMessage> handleJsonProcessingException() {
        ExceptionMessage error = new ExceptionMessage(
                "Error: JSON could not be parsed correctly",
                Instant.now(),
                HttpStatus.CONFLICT.name());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionMessage> handleResponseStatusException(ResponseStatusException e) {
        ExceptionMessage error = new ExceptionMessage(
                "Error: "+e.getMessage(),
                Instant.now(),
                HttpStatus.BAD_REQUEST.name());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
