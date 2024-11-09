package com.api.reservamed.service.validations.scheduling;

import com.api.reservamed.dtos.RequestAppointmentDTO;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.model.Queue;
import com.api.reservamed.repositories.AppointmentRepository;
import com.api.reservamed.service.DoctorsService;
import com.api.reservamed.service.PatientService;
import com.api.reservamed.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorFreeDoctorOnTime implements ValidationAppointmentScheduling {
    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private QueueService queueService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorsService doctorsService;

    @Override
    public void validate(RequestAppointmentDTO data) {
        if((!repository.consultaDisponibilidadeMedicoNoHorario(data.doctorId(), data.date()))){
            insertQueue(data);
            throw new ValidationException("Medico não está disponível no horário alocado! Mas foi colocado na fila de espera.");
        }
    }

    private void insertQueue(RequestAppointmentDTO data){
        var queuePosition = queueService.positionQueue(data.doctorId(), data.date());
        var doctor = doctorsService.getById(data.doctorId());
        var patient = patientService.getByCpf(data.patientCpf());

        queuePosition += 1;

        var queue = new Queue(doctor, patient, data.date(), data.type());

        queue.setQueue_position(queuePosition);
        queueService.insertQueue(queue);
    }
}
