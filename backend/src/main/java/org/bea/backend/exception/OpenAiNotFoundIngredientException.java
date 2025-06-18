package org.bea.backend.exception;

public class OpenAiNotFoundIngredientException extends RuntimeException {
    public OpenAiNotFoundIngredientException(String message) {
        super(message);
    }
}
