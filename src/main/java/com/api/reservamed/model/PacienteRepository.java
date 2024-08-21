package com.api.reservamed.model;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, String> {

}
