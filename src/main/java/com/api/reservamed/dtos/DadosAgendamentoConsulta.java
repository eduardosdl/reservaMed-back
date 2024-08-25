package com.api.reservamed.dtos;

import com.api.reservamed.model.TypeConsult;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(Long id_doctor,
                                       @NotNull Long id_patient,
                                       @NotNull @Future LocalDateTime date,
                                       String speciality,
                                       TypeConsult type) {
}
