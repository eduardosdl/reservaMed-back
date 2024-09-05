package com.api.reservamed.service.validations.agendamento;

import com.api.reservamed.dtos.DadosAgendamentoConsulta;
import com.api.reservamed.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta{

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsuta = dados.date();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsuta).toMinutes();
        if(diferencaEmMinutos < 30){
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
