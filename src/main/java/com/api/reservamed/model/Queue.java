package com.api.reservamed.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "queue")
@Entity(name = "queue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int queue_position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctors doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Enum
    private String type_consult;

    private LocalDateTime date;

    private LocalDateTime date_cancellation;

    private String cancellation_reason;

    private String status;

    public Queue(Doctors doctor, Patient patient, LocalDateTime date, TypeConsult type) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.type_consult = type.getDescription();
    }

    public Queue(Long id, Doctors doctor, Patient patient, LocalDateTime date, TypeConsult type) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.type_consult = type.getDescription();
    }
}
