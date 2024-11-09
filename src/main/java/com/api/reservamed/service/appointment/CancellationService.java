package com.api.reservamed.service.appointment;

import com.api.reservamed.dtos.RequestCancelAppointmentDTO;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.repositories.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CancellationService {

    @Autowired
    private AppointmentRepository repository;

    public void execute(Long id, RequestCancelAppointmentDTO cancellationData) {
        var appointment = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada"));

        if(cancellationData.reason() == null || cancellationData.reason().isEmpty()) {
            throw new IllegalArgumentException("O motivo do cancelamento é obrigatório");
        }

        if (appointment.getDate().isBefore(LocalDateTime.now())) {
            throw new ValidationException("A consulta não pode ser cancelada pois a data já passou");
        }

        validateAppointmentComplete(appointment.getStatus());

        // TODO: Implementar validação de janela de 24h entre atendimento e cancelamento

        appointment.setStatus("C");
        appointment.setCancellation_reason(cancellationData.reason());
        repository.save(appointment);
    }

    private void validateAppointmentComplete(String appointmentStatus) {
        if (appointmentStatus.equals("P")) {
            throw new ValidationException("A consulta já foi realizada");
        }
    }
}
