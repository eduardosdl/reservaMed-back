package com.api.reservamed.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PatientRegistrationData(
        @NotBlank String name,
        @NotNull LocalDate birthDate,
        @NotBlank String cpf,
        @NotBlank String cellPhone,
        @Email @NotBlank String email,
        String cep,
        String street,
        String state,
        String city,
        AllergyData allergyData,
        String medicalHistory){}

