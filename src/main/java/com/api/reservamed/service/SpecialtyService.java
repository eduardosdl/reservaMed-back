package com.api.reservamed.service;

import com.api.reservamed.dtos.CreateSpecialtyDTO;
import com.api.reservamed.dtos.UpdateSpecialtyDTO;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.model.Specialty;
import com.api.reservamed.repositories.SpecialtyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository repository;

    public List<Specialty> getAll() {
        return repository.findAll();
    }

    public Specialty getByspecialty(String specialty) {
        return repository.findByspecialty(specialty)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
    }

    public Specialty create(CreateSpecialtyDTO specialtyData) {
        try {
            validatespecialtyUniqueness(specialtyData.specialty());

            Specialty specialty = new Specialty(specialtyData);

            return repository.save(specialty);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao salvar paciente");
        }
    }

    public Specialty update(Long id, UpdateSpecialtyDTO specialtyData) {
        try {
            Specialty existingSpecialty = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));


            if (specialtyData.specialty() != null && !specialtyData.specialty().equals(existingSpecialty.getSpecialty())) {
                validatespecialtyUniqueness(specialtyData.specialty());
            }

            existingSpecialty.updateFromDTO(specialtyData);

            return existingSpecialty;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("houve um erro ao atualizar paciente");
        }
    }


//    public void deleteById(Long id) {
//        try {
//            repository.findById(id).ifPresent(specialty -> {
//                specialty.setActive(false);  // Remover esta parte
//            });
//        } catch (Exception e) {
//            throw new RuntimeException("Houve um erro ao excluir paciente");
//        }
//    }


    private void validatespecialtyUniqueness(String specialty) {
        repository.findByspecialty(specialty).ifPresent(existingSpecialty -> {
            throw new ValidationException("specialty já cadastrado");
        });
    }

    public void deleteByspecialty(String specialty) {
        Specialty existingSpecialty = repository.findByspecialty(specialty)
                .orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));
        
        repository.delete(existingSpecialty);

    }


    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}