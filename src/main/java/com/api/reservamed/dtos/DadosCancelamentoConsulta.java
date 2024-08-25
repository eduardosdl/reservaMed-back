package com.api.reservamed.dtos;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(@NotNull DadosDetalhamentoConsulta consulta,
                                        @NotNull String reason) {

}
