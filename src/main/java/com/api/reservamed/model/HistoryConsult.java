package com.api.reservamed.model;

import com.api.reservamed.dtos.DadosCancelamentoConsulta;
import com.api.reservamed.dtos.DadosConfirmaConsulta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "history_consult")
@Entity(name = "history_consult")
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
        this.id_doctor = dados.id_doctor();
        this.id_patient = dados.id_patient();
        this.date = dados.date();
        this.status = dados.status();
        this.diagnostic = dados.dadosDiagnostic().diagnostic();
        this.prescription = dados.dadosDiagnostic().prescription();
    }

    public HistoryConsult(DadosCancelamentoConsulta dados, Long id_doctor, Long id_patient, LocalDateTime date){
        this.id_doctor = id_doctor;
        this.id_patient = id_patient;
        this.date = date;
        this.date_cancellation = LocalDateTime.now();
        this.cancellation_reason = dados.reason();
        this.status = "C";
    }
}
