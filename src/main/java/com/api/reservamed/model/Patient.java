package com.api.reservamed.model;

import com.api.reservamed.dtos.PatientRegistrationData;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "patient")
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthDate;

    @Column(unique = true, nullable = false)
    private String cpf;

    private String cellPhone;
    private String email;
    private Boolean active = true;
    private String cep;
    private String street;
    private String state;
    private String city;

    public Patient(PatientRegistrationData requestPatient){
        this.name = requestPatient.name();
        this.birthDate = requestPatient.birthDate();
        this.cpf = requestPatient.cpf();
        this.cellPhone = requestPatient.cellPhone();
        this.email = requestPatient.email();
        this.cep = requestPatient.cep();
        this.state = requestPatient.state();
        this.city = requestPatient.city();
        this.street = requestPatient.street();
    }

}
