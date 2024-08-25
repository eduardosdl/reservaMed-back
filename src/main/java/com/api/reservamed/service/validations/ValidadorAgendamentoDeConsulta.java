package com.api.reservamed.service.validations;

import com.api.reservamed.dtos.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {
    void validar(DadosAgendamentoConsulta dados);
}
