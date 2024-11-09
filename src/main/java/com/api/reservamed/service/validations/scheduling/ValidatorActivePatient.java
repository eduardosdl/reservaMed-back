package com.api.reservamed.service.validations.scheduling;


import com.api.reservamed.dtos.RequestAppointmentDTO;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorActivePatient implements ValidationAppointmentScheduling {

    @Autowired
    private PatientService patientService;

    public void validate(RequestAppointmentDTO data) {
        var patient = patientService.getByCpf(data.patientCpf());
        if (!patient.getActive()) {
            throw new ValidationException("Consulta não pode ser agendada com médico excluído");
        }
    }
}
