package com.api.reservamed.service.validations.agendamento;

import com.api.reservamed.dtos.DadosAgendamentoConsulta;
import com.api.reservamed.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioPreferencial implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados){
        if(dados.type().name() .equals("PEDIATRIC")){
            validarHorarioPediatrico(dados);
        } else if (dados.type().name() .equals("SPECIALIZED")) {
            validadorHorarioEspecializado(dados);
        }

    }

    private void validarHorarioPediatrico(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.date();
        var antesDas9 = dataConsulta.getHour() < 9;
        var depoisDas16 = dataConsulta.getHour() > 16;
        if (antesDas9 || depoisDas16){
            throw new ValidacaoException("Data da consulta fora do horário da consulta do tipo pediátrico, 9HS AS 16H");
        }
    }

    private void validadorHorarioEspecializado(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.date();
        var antesDas9 = dataConsulta.getHour() < 9;
        var depoisDas16 = dataConsulta.getHour() > 16;
        if (antesDas9 || depoisDas16){
            throw new ValidacaoException("Data da consulta fora do horário da consulta do tipo especializado, 9HS AS 16H");
        }
    }
}
