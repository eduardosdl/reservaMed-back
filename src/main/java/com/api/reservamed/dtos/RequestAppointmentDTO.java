package com.api.reservamed.dtos;

import com.api.reservamed.model.TypeConsult;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RequestAppointmentDTO(
        @NotNull Long doctorId,
        @NotNull String patientCpf,
        @NotNull @Future LocalDateTime date,
        @NotNull TypeConsult type
) {
}
