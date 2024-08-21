package com.api.reservamed.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record DadosCadastroPaciente(Long id,
                                    @NotBlank String nome,
                                    @NotBlank LocalDate dataNascimento,
                                    @NotBlank String cpf,
                                    @NotBlank String telefone,
                                    @Email @NotBlank String email) {
}
