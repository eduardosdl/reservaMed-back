package com.api.reservamed.dtos;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long id_doctor, Long id_patient, LocalDateTime date) {
}
