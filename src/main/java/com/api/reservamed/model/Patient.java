package com.api.reservamed.model;

import com.api.reservamed.dtos.PatientRegistrationData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "patient")
@Table(name = "patient")
@Getter
@Setter
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
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Patient(PatientRegistrationData requestPatient){
        this.name = requestPatient.name();
        this.birthDate = requestPatient.birthDate();
        this.cpf = requestPatient.cpf();
        this.cellPhone = requestPatient.cellPhone();
        this.email = requestPatient.email();
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }
}
