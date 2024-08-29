package com.api.reservamed.dtos;

import com.api.reservamed.model.Consult;
import com.api.reservamed.model.Doctors;
import com.api.reservamed.model.Patient;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.time.LocalDateTime;

public record DadosConfirmaConsulta (
                                     Long id,
                                     Long id_doctor,
                                     Long id_patient,
                                     String type_consult,
                                     LocalDateTime date,
                                     String status,
                                     @Valid DadosDiagnostic dadosDiagnostic){
}
