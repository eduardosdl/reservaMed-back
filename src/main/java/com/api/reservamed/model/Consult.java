package com.api.reservamed.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "consult")
@Entity(name = "consult")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctors doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Enum
    private TypeConsult type_consult;

    private LocalDateTime date;

    private LocalDateTime date_cancellation;

    private String cancellation_reason;

    private String status;

    public Consult(Doctors doctor, Patient patient, LocalDateTime date, TypeConsult type) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.status = "A";
        this.type_consult = type;
    }

    public Consult(Long id, Doctors doctor, Patient patient, LocalDateTime date, TypeConsult type) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.status = "A";
        this.type_consult = type;
    }
}
