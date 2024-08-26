package com.api.reservamed.dtos;

import com.api.reservamed.model.Consult;
import jakarta.validation.Valid;

public record DadosConfirmaConsulta (Consult consult, @Valid DadosDiagnostic dadosDiagnostic){
}
