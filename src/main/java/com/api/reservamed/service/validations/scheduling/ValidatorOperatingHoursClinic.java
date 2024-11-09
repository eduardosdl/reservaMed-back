package com.api.reservamed.service.validations.scheduling;

import com.api.reservamed.dtos.RequestAppointmentDTO;
import com.api.reservamed.infra.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidatorOperatingHoursClinic implements ValidationAppointmentScheduling {

    public void validate(RequestAppointmentDTO data){
        var consultDate = data.date();

        var sunday = consultDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeClinicOpening = consultDate.getHour() < 7;
        var afterClinicOpening = consultDate.getHour() > 18;
        if (sunday || beforeClinicOpening || afterClinicOpening){
            throw new ValidationException("Data da consulta fora do horário de funcionamento da clínica!");
        }
    }
}
