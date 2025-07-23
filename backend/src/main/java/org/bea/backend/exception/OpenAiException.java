package org.bea.backend.exception;

public class OpenAiException extends RuntimeException {
    public OpenAiException(String message) {
        super(message);
    }
}
