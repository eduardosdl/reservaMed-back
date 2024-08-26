package com.api.reservamed.service.validations;

import com.api.reservamed.dtos.DadosAgendamentoConsulta;
import com.api.reservamed.infra.ValidacaoException;
import com.api.reservamed.repositories.ConsultRepository;

public class ValidadorMedicoLivreNoHorario implements ValidadorAgendamentoDeConsulta{
    private ConsultRepository repository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        if(!(repository.consultaDisponibilidadeMedicoNoHorario(dados.id_doctor(), dados.date()))){
            throw new ValidacaoException("Medico não está disponível no horário alocado! ");
        }
    }
}
