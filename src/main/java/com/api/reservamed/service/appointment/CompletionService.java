package com.api.reservamed.service.appointment;

import com.api.reservamed.dtos.RequestCompleteAppointmentDTO;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.repositories.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompletionService {
    @Autowired
    private AppointmentRepository repository;
    public void execute(Long id, RequestCompleteAppointmentDTO completionData) {
        var appointment = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada"));

        validateCanceledAppointment(appointment.getStatus());

        appointment.setStatus("P");
        appointment.setDescription(completionData.description());
        repository.save(appointment);
    }

    private void validateCanceledAppointment(String status) {
        if (status.equals("C")) {
            throw new ValidationException("Não é possível completar uma consulta cancelada");
        }
    }
}
