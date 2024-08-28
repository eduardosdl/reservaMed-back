package com.api.reservamed.controller;

import com.api.reservamed.dtos.PatientRegistrationData;
import com.api.reservamed.infra.exception.ErrorResponse;
import com.api.reservamed.model.Patient;
import com.api.reservamed.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @GetMapping
    public ResponseEntity<Iterable<Patient>> getAll() {
        Iterable<Patient> allPatients = repository.findAll();
        return ResponseEntity.ok(allPatients);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Patient> getByCpf(@PathVariable String cpf) {
        Patient patient = repository.findByCpf(cpf);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Object> created(@RequestBody @Valid PatientRegistrationData data) {
        if (repository.existsByCpf(data.cpf())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("CPF já existe no banco de dados"));
        }

        int age = Period.between(data.birthDate(), LocalDate.now()).getYears();

        Patient newPatient = new Patient(data);
        var patient = repository.save(newPatient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient); // 201 Created
    }


    @Transactional
    @PutMapping("/{cpf}")
    public ResponseEntity<Object> update(@PathVariable String cpf, @RequestBody @Valid PatientRegistrationData data) {
        Patient patient = repository.findByCpf(cpf);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!data.cpf().equals(cpf) && repository.existsByCpf(data.cpf())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("CPF já existe no banco de dados"));
        }

        patient.setName(data.name());
        patient.setEmail(data.email());
        patient.setBirthDate(data.birthDate());
        patient.setCpf(data.cpf());
        patient.setCellPhone(data.cellPhone());
        patient.setCep(data.cep());
        patient.setStreet(data.street());
        patient.setState(data.state());
        patient.setCity(data.city());
        patient.setMedicalHistory(data.medicalHistory());
        patient.setGuardianCpf(data.guardianCpf());

        repository.save(patient);
        return ResponseEntity.ok(patient);
    }

    @Transactional
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable String cpf) {
        Patient patient = repository.findByCpf(cpf);
        if (patient != null) {
            repository.delete(patient);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
