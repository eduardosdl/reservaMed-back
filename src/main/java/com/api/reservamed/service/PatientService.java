package com.api.reservamed.service;

import com.api.reservamed.dtos.PatientUpdateData;
import com.api.reservamed.model.Patient;
import com.api.reservamed.repositories.PatientRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repository;

    public Patient updatePatient(PatientUpdateData data, String cpf){
        try{
            Patient patient = repository.findByCpf(cpf);
            if(data.name()!=null){
                patient.setName(data.name());
            }

            if(data.email()!=null){
                patient.setEmail(data.email());
            }

            if(data.birthDate()!=null){
                patient.setBirthDate(data.birthDate());
            }

            if(data.cpf()!=null){
                patient.setCpf(data.cpf());
            }

            if(data.cellPhone()!=null){
                patient.setCellPhone(data.cellPhone());
            }

            if(data.cep()!=null){
                patient.setCep(data.cep());
            }

            if(data.street()!=null){
                patient.setStreet(data.street());
            }

            if(data.state()!=null){
                patient.setState(data.state());
            }

            if(data.city()!=null){
                patient.setCity(data.city());
            }

            if(data.medicalHistory()!=null){
                patient.setMedicalHistory(data.medicalHistory());
            }

            if(data.guardianCpf()!=null){
                patient.setGuardianCpf(data.guardianCpf());
            }

            return repository.save(patient);
        }catch (Exception e){
            throw new ValidationException("Erro ao atualizar um paciente: " + e.getMessage());
        }
    }
}
