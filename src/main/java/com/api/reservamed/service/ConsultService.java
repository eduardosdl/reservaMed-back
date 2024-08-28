package com.api.reservamed.service;

import com.api.reservamed.infra.ValidacaoException;
import com.api.reservamed.model.Consult;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultService {
    @Autowired
    ConsultRepository repository;

    @Autowired
    PatientRepository patientRepository;

    public List<Consult> getConsultsByPatientCpf(String cpf) {
        var patientExists = patientRepository.existsByCpf(cpf);

        if (!patientExists) {
            throw new ValidacaoException("CPF do paciente informado não existe");
        }

        return repository.findByPatientCpf(cpf);
    }
}
