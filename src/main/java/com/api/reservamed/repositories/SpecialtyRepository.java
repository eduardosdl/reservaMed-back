package com.api.reservamed.repositories;

import com.api.reservamed.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    Optional<Specialty> findByspecialty(String specialty);

    List<Specialty> findAll();

    boolean existsByspecialty(String specialty);

    boolean existsBySpecialty(String specialty);
}




