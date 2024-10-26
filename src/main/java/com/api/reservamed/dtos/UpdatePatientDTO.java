package com.api.reservamed.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UpdatePatientDTO(
        String name,
        LocalDate birthDate,
        @Pattern(regexp = "\\d{11}", message = "O campo CPF deve ter 11 dígitos") String cpf,
        @Pattern(regexp = "\\d{11}", message = "O campo Celular deve ter 11 dígitos") String cellPhone,
        @Email String email,
        String cep,
        String street,
        String state,
        String city,
        String allergy,
        String medicalHistory,
        String guardianCpf
) {
}
