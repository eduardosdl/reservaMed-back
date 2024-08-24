package com.api.reservamed.dtos;

import jakarta.validation.constraints.NotNull;


public record DoctorDto(@NotNull String name, @NotNull String crm, @NotNull String specialty, @NotNull String cellPhone) {
}
