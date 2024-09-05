package com.api.reservamed.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PatientRegistrationData(
        @NotBlank String name,
        @NotNull LocalDate birthDate,
        @NotBlank @Pattern(regexp = "\\d{11}", message = "O campo CPF deve ter 11 dígitos") String cpf,
        @NotBlank @Pattern(regexp = "\\d{11}", message = "O campo Celular deve ter 11 dígitos") String cellPhone,
        @Email @NotBlank String email,
        String cep,
        String street,
        String state,
        String city,
        String allergy,
        String medicalHistory,
        String guardianCpf ){}

