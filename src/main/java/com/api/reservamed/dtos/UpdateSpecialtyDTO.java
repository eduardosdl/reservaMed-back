package com.api.reservamed.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UpdateSpecialtyDTO(
        String specialty,
        String description
)
    {
}
