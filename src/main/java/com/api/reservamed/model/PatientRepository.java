package com.api.reservamed.model;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, String> {
    Patient findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
