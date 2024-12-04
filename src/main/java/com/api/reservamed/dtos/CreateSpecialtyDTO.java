package com.api.reservamed.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateSpecialtyDTO(
        @NotBlank String specialty,
        @NotBlank String description

) {
}

