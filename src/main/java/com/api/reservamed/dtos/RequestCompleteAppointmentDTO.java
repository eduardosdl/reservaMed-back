package com.api.reservamed.dtos;

import jakarta.validation.constraints.NotNull;

public record RequestCompleteAppointmentDTO(
        @NotNull String description
) {
}
