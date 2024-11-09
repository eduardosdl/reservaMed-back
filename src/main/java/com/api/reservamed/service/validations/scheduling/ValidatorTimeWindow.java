package com.api.reservamed.service.validations.scheduling;

import com.api.reservamed.dtos.RequestAppointmentDTO;
import com.api.reservamed.infra.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidatorTimeWindow implements ValidationAppointmentScheduling {

    public void validate(RequestAppointmentDTO data) {
        var consultData = data.date();
        var now = LocalDateTime.now();
        var minutesDifference = Duration.between(now, consultData).toMinutes();

        if (minutesDifference < 30) {
            throw new ValidationException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
