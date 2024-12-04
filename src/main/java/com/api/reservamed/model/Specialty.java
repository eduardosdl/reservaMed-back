package com.api.reservamed.model;

import com.api.reservamed.dtos.CreateSpecialtyDTO;
import com.api.reservamed.dtos.UpdateSpecialtyDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "specialty")
@Table(name = "specialty")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialty;
    private String description;

    public Specialty(CreateSpecialtyDTO requestSpecialty) {
        this.specialty = requestSpecialty.specialty();
        this.description = requestSpecialty.description();
    }

    public void updateFromDTO(UpdateSpecialtyDTO newSpecialtyData) {
        if (newSpecialtyData.specialty() != null) this.specialty = newSpecialtyData.specialty();
        if (newSpecialtyData.description() != null) this.description = newSpecialtyData.description();
    }
}
