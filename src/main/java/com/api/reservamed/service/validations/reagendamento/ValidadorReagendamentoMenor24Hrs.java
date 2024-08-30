package com.api.reservamed.service.validations.reagendamento;

import com.api.reservamed.dtos.DadosReagendamentoConsulta;
import com.api.reservamed.infra.ValidacaoException;
import com.api.reservamed.repositories.ConsultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

//@Component
public class ValidadorReagendamentoMenor24Hrs implements ValidadorReagendamentoDeConsulta {

    @Autowired
    private ConsultRepository consultRepository;

    public void validar(DadosReagendamentoConsulta dados){
        var dataReagendamentoConsulta = dados.date();
        var consult = consultRepository.getReferenceById(dados.id());
        var dataConsulta = consult.getDate();
        var diferencaEmMinutos = Duration.between(dataConsulta, dataReagendamentoConsulta).toHours();
        if(diferencaEmMinutos < 24){
            throw new ValidacaoException("Consulta não pode ser reagendada com menos de 24h");
        }
    }
}
