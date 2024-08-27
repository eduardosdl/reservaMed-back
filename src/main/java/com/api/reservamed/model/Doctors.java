package com.api.reservamed.model;

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
public class Doctors {
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
}
