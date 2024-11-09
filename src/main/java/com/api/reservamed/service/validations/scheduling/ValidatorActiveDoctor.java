package com.api.reservamed.service.validations.scheduling;

import com.api.reservamed.dtos.RequestAppointmentDTO;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorActiveDoctor implements ValidationAppointmentScheduling {

    @Autowired
    private DoctorsService doctorsService;

    public void validate(RequestAppointmentDTO data){
        var doctor = doctorsService.getById(data.doctorId());
        if (!doctor.getActive()){
            throw new ValidationException("Consulta não pode ser agendada com médico excluído");
        }
    }
}
