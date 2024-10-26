package com.api.reservamed.service;

import com.api.reservamed.dtos.CreatePatientDTO;
import com.api.reservamed.dtos.UpdatePatientDTO;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.model.Patient;
import com.api.reservamed.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repository;

    public List<Patient> getAll() {
        return repository.findAllByActiveTrue();
    }

    public Patient getByCpf(String cpf) {
        return repository.findByCpf(cpf)
                .orElseThrow(() -> new ValidationException("Paciente não encontrado"));
    }

    public Patient create(CreatePatientDTO patientData) {
        try {
            validateCpfUniqueness(patientData.cpf());

            Patient patient = new Patient(patientData);

            return repository.save(patient);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao salvar paciente");
        }
    }

    public Patient update(Long id, UpdatePatientDTO patientData) {
        try {
            Patient existingPatient = repository.findById(id)
                    .orElseThrow(() -> new ValidationException("Paciente não encontrado"));


            if (patientData.cpf() != null && !patientData.cpf().equals(existingPatient.getCpf())) {
                validateCpfUniqueness(patientData.cpf());
            }

            existingPatient.updateFromDTO(patientData);

            return existingPatient;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ValidationException("houve um erro ao atualizar paciente");
        }
    }

    public void deleteByCpf(String cpf) {
        try {
            repository.findByCpf(cpf).ifPresent(patient -> {
                patient.setActive(false);
            });
        } catch (Exception e) {
            throw new ValidationException("Houve um erro ao excluir paciente");
        }
    }

    public void deleteById(Long id) {
        try {
            repository.findById(id).ifPresent(patient -> {
                patient.setActive(false);
            });
        } catch (Exception e) {
            throw new ValidationException("Houve um erro ao excluir paciente");
        }
    }

    private void validateCpfUniqueness(String cpf) {
        repository.findByCpf(cpf).ifPresent(existingPatient -> {
            throw new ValidationException("CPF já cadastrado");
        });
    }
}