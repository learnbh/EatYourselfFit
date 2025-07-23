package org.bea.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Jobs")
public record Job(
        String id,
        String name,
        String description,
        String status,
        Boolean runOnce,
        Boolean runDaily
) {
}
