package com.api.reservamed.repositories;


import com.api.reservamed.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByCrm(String crm);

    List<Doctor> findAllByActiveTrue();

    Optional<Doctor> findByCellPhone(String cellPhone);

    void deleteByCrm(String crm);

    @Query("""
            select active from doctors
            where id = :id
            """)
    Boolean findAtivoById(@Param("id") Long id);
}

