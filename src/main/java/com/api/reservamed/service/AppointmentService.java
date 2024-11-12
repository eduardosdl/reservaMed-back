package com.api.reservamed.service;

import com.api.reservamed.dtos.RequestAppointmentDTO;
import com.api.reservamed.dtos.RequestCancelAppointmentDTO;
import com.api.reservamed.dtos.RequestCompleteAppointmentDTO;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.model.Appointment;
import com.api.reservamed.repositories.AppointmentRepository;
import com.api.reservamed.repositories.PatientRepository;
import com.api.reservamed.service.appointment.CancellationService;
import com.api.reservamed.service.appointment.CompletionService;
import com.api.reservamed.service.appointment.ReschedulingService;
import com.api.reservamed.service.appointment.SchedulingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository repository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorsService doctorsService;

    @Autowired
    SchedulingService schedulingService;

    @Autowired
    ReschedulingService reschedulingService;

    @Autowired
    CancellationService cancellationService;

    @Autowired
    CompletionService completionService;

    public List<Appointment> listAllPending() {
        try {
            return repository.findAllActive();
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao buscar consultas");
        }
    }

    public List<Appointment> getAppointmentByPatientCpf(String cpf) {
        var patientExists = patientRepository.existsByCpf(cpf);

        if (!patientExists) {
            throw new ValidationException("CPF do paciente informado não existe");
        }

        return repository.findByPatientCpf(cpf);
    }

    public List<Appointment> getAppointmentByDoctorCrmAndStatusAndDate(String crm, String status, LocalDateTime date) {
        try {
            var doctor = doctorsService.getByCrm(crm);
            return repository.findByDoctorCrmAndDateAndStatus(doctor.getCrm(), status, date);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao buscar consultas");
        }
    }

    public long countAppointmentByDoctorCrmAndStatusAndDate(String crm, String status, LocalDateTime date) {
        try {
            var doctor = doctorsService.getByCrm(crm);
            return repository.countByDoctorCrmAndDateAndStatus(doctor.getCrm(), status, date);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao contar consultas");
        }
    }

    public List<Appointment> getAppointmentCompletedByDoctorCrmAndPatientCpf(String crm, String cpf) {
        try {
            var doctor = doctorsService.getByCrm(crm);
            return repository.findByDoctorCrmAndPatientCpfAndStatus(doctor.getCrm(), cpf,"P");
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao buscar consultas");
        }
    }

    public Appointment scheduleAppointment(RequestAppointmentDTO appointmentData) {
        try {
            return repository.save(schedulingService.execute(appointmentData));
        } catch (ValidationException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao agendar consulta");
        }
    }

    public Appointment rescheduleAppointment(Long id, RequestAppointmentDTO appointmentData) {
        try {
            return reschedulingService.execute(id, appointmentData);
        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao reagendar consulta");
        }
    }

    public void completeAppointment(Long id, RequestCompleteAppointmentDTO completeData) {
        try {
            completionService.execute(id, completeData);
        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao completar consulta");
        }
    }

    public void cancelAppointment(Long id, RequestCancelAppointmentDTO cancellationData) {
        try {
            cancellationService.execute(id, cancellationData);
        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao cancelar consulta");
        }
    }
}
