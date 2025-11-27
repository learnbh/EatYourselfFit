package org.bea.backend.service;

import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class DateService {

    public Instant getCurrentInstant() {
        return Instant.now();
    }
}
