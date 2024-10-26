package com.api.reservamed.repositories;

import com.api.reservamed.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByCpf(String cpf);

    List<Patient> findAllByActiveTrue();

    boolean existsByCpf(String cpf);

    @Query("select active from patient where cpf = :cpf")
    boolean findActiveByCpf(String cpf);
}




