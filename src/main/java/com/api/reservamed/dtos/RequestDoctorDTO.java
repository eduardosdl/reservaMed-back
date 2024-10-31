package com.api.reservamed.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record RequestDoctorDTO(@NotNull String name,
                               @NotNull @Pattern(regexp = "\\d{4,6}") String crm,
                               @NotNull String specialty,
                               @NotNull String cellPhone) {
}
