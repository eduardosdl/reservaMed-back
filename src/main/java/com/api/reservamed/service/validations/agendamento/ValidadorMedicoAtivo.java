package com.api.reservamed.service.validations.agendamento;

import com.api.reservamed.dtos.DadosAgendamentoConsulta;
import com.api.reservamed.infra.exception.ValidacaoException;
import com.api.reservamed.repositories.DoctorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta{

    @Autowired
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
