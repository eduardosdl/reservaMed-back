package com.api.reservamed.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = false)
public record PatientUpdateData(
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
                                String guardianCpf ){

}

