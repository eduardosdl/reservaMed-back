package com.api.reservamed.dtos;

import jakarta.validation.constraints.NotBlank;

public record DadosDiagnostic(String diagnostic, String prescription) {
}
