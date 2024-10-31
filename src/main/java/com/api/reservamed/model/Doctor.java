package com.api.reservamed.model;

import com.api.reservamed.dtos.RequestDoctorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "doctors")
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column (unique = true, nullable = false)
    private String crm;
    private String specialty;

    @Column(name = "cell_phone")
    private String cellPhone;
    private Boolean active = true;

    public Doctor(RequestDoctorDTO data) {
        this.name = data.name();
        this.crm = data.crm();
        this.cellPhone = data.cellPhone();
        this.specialty = data.specialty();
    }

    public void updateFromDTO(RequestDoctorDTO data) {
        this.name = data.name();
        this.crm = data.crm();
        this.cellPhone = data.cellPhone();
        this.specialty = data.specialty();
    }
}
