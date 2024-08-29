package com.api.reservamed.service.validations.reagendamento;

import com.api.reservamed.dtos.DadosReagendamentoConsulta;
import com.api.reservamed.infra.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioPreferencialReag implements ValidadorReagendamentoDeConsulta {

    public void validar(DadosReagendamentoConsulta dados){
        System.out.println("Chegou aqui 1");
        System.out.println("Tipo: " + dados.type().getDescription());
        if(dados.type().name() .equals("PEDIATRIC")){
            validarHorarioPediatrico(dados);
        } else if (dados.type().name() .equals("SPECIALIZED")) {
            validadorHorarioEspecializado(dados);
        }

    }

    private void validarHorarioPediatrico(DadosReagendamentoConsulta dados){
        var dataConsulta = dados.date();
        var antesDas9 = dataConsulta.getHour() < 9;
        var depoisDas16 = dataConsulta.getHour() > 16;
        if (antesDas9 || depoisDas16){
            throw new ValidacaoException("Data da consulta fora do horário da consulta do tipo pediátrico, 9HS AS 16H");
        }
    }

    private void validadorHorarioEspecializado(DadosReagendamentoConsulta dados){
        var dataConsulta = dados.date();
        var antesDas9 = dataConsulta.getHour() < 9;
        var depoisDas16 = dataConsulta.getHour() > 16;
        if (antesDas9 || depoisDas16){
            throw new ValidacaoException("Data da consulta fora do horário da consulta do tipo especializado, 9HS AS 16H");
        }
    }
}
