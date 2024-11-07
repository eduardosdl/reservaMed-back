package com.api.reservamed.service;

import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.model.Consult;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultService {
    @Autowired
    ConsultRepository repository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorsService doctorsService;

    public List<Consult> getConsultsByPatientCpf(String cpf) {
        var patientExists = patientRepository.existsByCpf(cpf);

        if (!patientExists) {
            throw new ValidationException("CPF do paciente informado não existe");
        }

        return repository.findByPatientCpf(cpf);
    }

    public List<Consult> getConsultsByDoctorCrmAndDate(String crm, LocalDateTime date) {
        try {
            var doctor = doctorsService.getByCrm(crm);

            return repository.findByDoctorCrmAndDate(doctor.getCrm(), date);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao buscar consultas");
        }
    }
}
