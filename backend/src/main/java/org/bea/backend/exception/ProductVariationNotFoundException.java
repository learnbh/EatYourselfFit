package org.bea.backend.exception;

public class ProductVariationNotFoundException extends RuntimeException {
    public ProductVariationNotFoundException(String message) {
        super(message);
    }
}
