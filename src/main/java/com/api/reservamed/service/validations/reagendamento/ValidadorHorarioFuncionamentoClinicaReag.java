package com.api.reservamed.service.validations.reagendamento;

import com.api.reservamed.dtos.DadosReagendamentoConsulta;
import com.api.reservamed.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinicaReag implements ValidadorReagendamentoDeConsulta {

    public void validar(DadosReagendamentoConsulta dados){
        var dataConsulta = dados.date();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;
        if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica){
            throw new ValidacaoException("Data da consulta fora do horário de funcionamento da clínica!");
        }
    }
}
