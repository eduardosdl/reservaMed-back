package com.api.reservamed.model;

import com.api.reservamed.dtos.CreatePatientDTO;
import com.api.reservamed.dtos.UpdatePatientDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private String allergy;
    private String medicalHistory;
    private String guardianCpf;

    public Patient(CreatePatientDTO requestPatient) {
        this.name = requestPatient.name();
        this.birthDate = requestPatient.birthDate();
        this.cpf = requestPatient.cpf();
        this.cellPhone = requestPatient.cellPhone();
        this.email = requestPatient.email();
        this.cep = requestPatient.cep();
        this.state = requestPatient.state();
        this.city = requestPatient.city();
        this.street = requestPatient.street();
        this.allergy = requestPatient.allergy();
        this.medicalHistory = requestPatient.medicalHistory();

        if (requestPatient.birthDate().isAfter(LocalDate.now().minusYears(18))) {
            this.guardianCpf = requestPatient.guardianCpf();
        }
    }

    public void updateFromDTO(UpdatePatientDTO newPatientData) {
        if (newPatientData.name() != null) this.name = newPatientData.name();
        if (newPatientData.birthDate() != null) this.birthDate = newPatientData.birthDate();
        if (newPatientData.cpf() != null) this.cpf = newPatientData.cpf();
        if (newPatientData.cellPhone() != null) this.cellPhone = newPatientData.cellPhone();
        if (newPatientData.email() != null) this.email = newPatientData.email();
        if (newPatientData.cep() != null) this.cep = newPatientData.cep();
        if (newPatientData.street() != null) this.street = newPatientData.street();
        if (newPatientData.state() != null) this.state = newPatientData.state();
        if (newPatientData.city() != null) this.city = newPatientData.city();
        if (newPatientData.allergy() != null) this.allergy = newPatientData.allergy();
        if (newPatientData.medicalHistory() != null) this.medicalHistory = newPatientData.medicalHistory();

        if (newPatientData.birthDate() != null &&
                newPatientData.birthDate().isAfter(LocalDate.now().minusYears(18))) {
            this.guardianCpf = newPatientData.guardianCpf();
        }
    }
}
