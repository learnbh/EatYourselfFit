package org.bea.backend.exception;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorResponse(
        Map<String, String> messages,
        Instant timestamp,
        String status) {}
