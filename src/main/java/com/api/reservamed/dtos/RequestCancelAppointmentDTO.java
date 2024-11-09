package com.api.reservamed.dtos;

import jakarta.validation.constraints.NotNull;

public record RequestCancelAppointmentDTO(
        @NotNull String reason
) {
}
