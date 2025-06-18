package org.bea.backend.exception;

public class ProductVariationNotFound extends RuntimeException {
    public ProductVariationNotFound(String message) {
        super(message);
    }
}
