package com.api.reservamed.service.validations;

import com.api.reservamed.dtos.DadosAgendamentoConsulta;
import com.api.reservamed.infra.ValidacaoException;
import com.api.reservamed.repositories.DoctorsRepository;

public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta{

    private DoctorsRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        if(dados.id_doctor() == null){
            return;
        }

        var medicoEstaAtivo = repository.findAtivoById(dados.id_doctor());
        if (!medicoEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com médico excluído");
        }
    }
}
