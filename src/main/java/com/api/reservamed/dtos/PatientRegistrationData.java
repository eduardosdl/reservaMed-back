package com.api.reservamed.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PatientRegistrationData(
        @NotNull String name,
        @NotNull LocalDate birthDate,
        @NotNull String cpf,
        @NotNull String cellPhone,
        @NotNull @Email String email,
        @NotNull String cep,
        @NotNull String street,
        @NotNull String state,
        @NotNull String city,
        @NotNull String allergy,
        @NotNull String responsibleCpf
        ){}

