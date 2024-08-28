package com.api.reservamed.dtos;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long id_doctor, String cpf_patient, LocalDateTime date) {
}
