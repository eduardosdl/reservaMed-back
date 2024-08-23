package com.api.reservamed.repositories;


import com.api.reservamed.model.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctors, Long> {
    Doctors findByCrm(String cpf);
   void deleteByCrm(String crm);

}

