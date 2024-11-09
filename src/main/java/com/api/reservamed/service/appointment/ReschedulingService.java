package com.api.reservamed.service.appointment;

import com.api.reservamed.dtos.RequestAppointmentDTO;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.model.Appointment;
import com.api.reservamed.repositories.AppointmentRepository;
import com.api.reservamed.service.validations.scheduling.ValidationAppointmentScheduling;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReschedulingService {

//    @Autowired
//    private PatientService patientService;
//
//    @Autowired
//    private DoctorsService doctorService;

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private List<ValidationAppointmentScheduling> validations;

    public Appointment execute(Long consultId, RequestAppointmentDTO newAppointmentData) {
//        var patient = patientService.getByCpf(newAppointmentData.patientCpf());
//        var doctor = doctorService.getById(newAppointmentData.doctorId());

        var appointment = repository.findById(consultId).orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada"));

        validateAppointmentStatus(appointment.getStatus());
        validateDate(appointment.getDate());

        // TODO: Implementar validação de janela de horário entre atendimento e reagendamento

        if (newAppointmentData.date().isEqual(appointment.getDate())) {
           throw new ValidationException("A nova data deve ser diferente da data atual");
        }

        validations.forEach(v -> v.validate(newAppointmentData));

        appointment.setDate(newAppointmentData.date());
        repository.save(appointment);

        return appointment;
    }

    private void validateAppointmentStatus(String currentStatus) {
        switch (currentStatus) {
            case "C":
                throw new ValidationException("A consulta já foi cancelada");
            case "P":
                throw new ValidationException("A consulta já foi realizada");
            case "A":
                break;
        }
    }

    private void validateDate(LocalDateTime date) {
        if (date.isBefore(LocalDateTime.now())) {
            throw new ValidationException("A consulta não pode ser reagendada pois a data já passou");
        }
    }
}
