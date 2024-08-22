package com.api.reservamed.model;

import com.api.reservamed.dtos.PatientRegistrationData;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "paciente")
@Table(name = "paciente")
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

}
