package com.api.reservamed.dtos;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(@NotNull Long id,
                                        @NotNull String reason) {

}
