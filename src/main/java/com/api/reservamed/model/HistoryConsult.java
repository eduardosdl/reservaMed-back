package com.api.reservamed.model;

import com.api.reservamed.dtos.DadosCancelamentoConsulta;
import com.api.reservamed.dtos.DadosConfirmaConsulta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "consult")
@Entity(name = "consult")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryConsult {
    @Id
    private Long id_patient;
    private Long id_doctor;
    // Enum
    private TypeConsult type_consult;
    private LocalDateTime date;
    private LocalDateTime date_cancellation;
    private String cancellation_reason;
    private String status;
    private String diagnostic;
    private String prescription;

    public HistoryConsult(DadosConfirmaConsulta dados){
        this.id_doctor = dados.consult().getDoctor().getId();
        this.id_patient = dados.consult().getPatient().getId();
        this.date = dados.consult().getDate();
        this.date_cancellation = dados.consult().getDate_cancellation();
        this.cancellation_reason = dados.consult().getCancellation_reason();
        this.status = dados.consult().getStatus();
        this.diagnostic = dados.dadosDiagnostic().diagnostic();
        this.prescription = dados.dadosDiagnostic().prescription();
    }

    public HistoryConsult(DadosCancelamentoConsulta dados){
        this.id_doctor = dados.consulta().id_doctor();
        this.id_patient = dados.consulta().id_patient();
        this.date = dados.consulta().date();
        this.date_cancellation = LocalDateTime.now();
        this.cancellation_reason = dados.reason();
        this.status = "C";
    }
}
