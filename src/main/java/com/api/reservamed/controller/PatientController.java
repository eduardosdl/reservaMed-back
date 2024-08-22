package com.api.reservamed.controller;

import com.api.reservamed.dtos.PatientRegistrationData;
import com.api.reservamed.model.Patient;
import com.api.reservamed.model.PatientRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Void> created(@RequestBody @Valid PatientRegistrationData data) {
        if (repository.existsByCpf(data.cpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }
        Patient newPatient = new Patient(data);
        repository.save(newPatient);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
    }


    @PutMapping("/{cpf}")
    public ResponseEntity<Patient> update(@PathVariable String cpf, @RequestBody @Valid PatientRegistrationData data) {
        Patient patient = repository.findByCpf(cpf);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!data.cpf().equals(cpf) && repository.existsByCpf(data.cpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }

        patient.setName(data.name());
        patient.setEmail(data.email());
        patient.setBirthDate(data.birthDate());
        patient.setCpf(data.cpf());
        patient.setCellPhone(data.cellPhone());


        repository.save(patient);
        return ResponseEntity.ok(patient);
    }


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

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(String.valueOf(id))) {
            repository.deleteById(String.valueOf(id));
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
