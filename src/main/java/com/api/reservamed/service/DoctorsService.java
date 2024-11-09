package com.api.reservamed.service;

import com.api.reservamed.dtos.RequestDoctorDTO;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.model.Doctor;
import com.api.reservamed.repositories.DoctorsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DoctorsService {
    @Autowired
    DoctorsRepository repository;

    public List<Doctor> listAll() {
        try {
            return repository.findAllByActiveTrue();
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao buscar médicos");
        }
    }

    public Doctor getById(Long id) {
        try {
            return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao buscar médico");
        }
    }

    public Doctor getByCrm(String crm) {
        try {
            return repository.findByCrm(crm).orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao buscar CRM");
        }
    }

    public Doctor create(RequestDoctorDTO doctorData) {
        try {
            validateCrm(doctorData.crm());
            validateCellPhone(doctorData.cellPhone());

            Doctor newDoctor = new Doctor(doctorData);

            return repository.save(newDoctor);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao cadastrar médico");
        }
    }

    public Doctor update(String crm, RequestDoctorDTO doctorData) {
        try {
            Doctor doctor = repository.findByCrm(crm).orElseThrow(EntityNotFoundException::new);

            if (doctorData.crm() != null && !Objects.equals(doctor.getCrm(), doctorData.crm())) {
                validateCrm(doctorData.crm());
            }

            if (doctorData.cellPhone() != null && !Objects.equals(doctor.getCellPhone(), doctorData.cellPhone())) {
                validateCellPhone(doctorData.cellPhone());
            }

            doctor.updateFromDTO(doctorData);

            return doctor;
        } catch (ValidationException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao atualizar médico");
        }
    }

    public void deleteByCrm(String crm) {
        try {
            repository.findByCrm(crm).ifPresent(existingDoctor -> existingDoctor.setActive(false));
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao tentar excluir médico");
        }
    }

    private void validateCrm(String crm) {
        if (crm == null) throw new ValidationException("Informe um CRM");

        repository.findByCrm(crm).ifPresent(existingDoctor -> {
            throw new ValidationException("CRM já cadastrado");
        });
    }

    private void validateCellPhone(String phoneNumber) {
        if (phoneNumber == null) throw new ValidationException("Informe um número de telefone");

        repository.findByCellPhone(phoneNumber).ifPresent(existingDoctor -> {
            throw new ValidationException("Número de telefone ja cadastrado");
        });
    }
}