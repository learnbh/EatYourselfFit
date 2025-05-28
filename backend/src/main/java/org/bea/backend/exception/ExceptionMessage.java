package org.bea.backend.exception;

import java.time.Instant;

public record ExceptionMessage(
        String error,
        Instant timestamp,
        String status)
{}
