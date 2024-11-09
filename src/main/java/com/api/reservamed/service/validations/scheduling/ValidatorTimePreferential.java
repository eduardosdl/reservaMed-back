package com.api.reservamed.service.validations.scheduling;

import com.api.reservamed.dtos.RequestAppointmentDTO;
import com.api.reservamed.infra.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ValidatorTimePreferential implements ValidationAppointmentScheduling {

    public void validate(RequestAppointmentDTO data){
        if(data.type().name() .equals("PEDIATRIC")){
            validarHorarioPediatrico(data);
        } else if (data.type().name() .equals("SPECIALIZED")) {
            validadorHorarioEspecializado(data);
        }

    }

    private void validarHorarioPediatrico(RequestAppointmentDTO data){
        var consultDate = data.date();
        var beforeNineOClock= consultDate.getHour() < 9;
        var afterNineOClock = consultDate.getHour() > 16;
        if (beforeNineOClock || afterNineOClock){
            throw new ValidationException("Data da consulta fora do horário da consulta do tipo pediátrico, 9HS AS 16H");
        }
    }

    private void validadorHorarioEspecializado(RequestAppointmentDTO data){
        var consultDate = data.date();
        var beforeNineOClock= consultDate.getHour() < 9;
        var afterNineOClock = consultDate.getHour() > 16;
        if (beforeNineOClock || afterNineOClock){
            throw new ValidationException("Data da consulta fora do horário da consulta do tipo especializado, 9HS AS 16H");
        }
    }
}
