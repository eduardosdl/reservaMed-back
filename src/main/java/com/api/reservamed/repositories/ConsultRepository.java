package com.api.reservamed.repositories;

import com.api.reservamed.model.Consult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultRepository extends JpaRepository<Consult, Long> {
}
