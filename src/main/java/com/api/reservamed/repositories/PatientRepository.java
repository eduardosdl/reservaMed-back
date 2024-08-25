package com.api.reservamed.repositories;

import com.api.reservamed.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
