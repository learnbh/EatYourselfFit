package org.bea.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDto(

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, message = "Name must have at least 1 character")
    String name,

    @NotNull(message = "Email cannot be null")
    @Size(min = 8, message = "Email must have at least 8 characters")
    String email,

    String imageUrl,

    @NotNull(message = "Type cannot be null")
    @Size(min = 5, message = "Type must have at least 5 characters")
    String type
) { }
