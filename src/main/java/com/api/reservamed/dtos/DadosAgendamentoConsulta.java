package com.api.reservamed.dtos;

import com.api.reservamed.model.TypeConsult;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        @NotNull Long id_doctor,
        @NotNull String cpf_patient,
        @NotNull @Future LocalDateTime date,
        @NotNull TypeConsult type
) {
}
