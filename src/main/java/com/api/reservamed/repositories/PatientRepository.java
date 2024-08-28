package com.api.reservamed.repositories;

import com.api.reservamed.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    @Query("select active from patient where cpf = :cpf")
    boolean findActiveByCpf(String cpf);

    @Query("""
            select active from
            patient where id = :id
            """)
    boolean findAtivoById(@Param("id") Long id);
}
