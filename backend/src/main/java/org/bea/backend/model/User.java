package org.bea.backend.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document("Users")
public record User(
    @Id
    String id,

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, message = "Name must have at least 1 character")
    String name,

    String email,

    @NotNull(message = "Role cannot be null")
    @Size(min = 4, message = "Role must have at least 4 characters")
    String role,

    String imageUrl,

    @NotNull(message = "Type cannot be null")
    @Size(min = 5, message = "Type must have at least 5 characters")
    String type,

    @NotNull(message = "Created At cannot be null")
    Instant createdAt,

    @NotNull(message = "Updated At cannot be null")
    Instant updatedAt
) {}
