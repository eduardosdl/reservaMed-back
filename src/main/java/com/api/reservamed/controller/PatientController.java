package com.api.reservamed.controller;

import com.api.reservamed.dtos.PatientRegistrationData;
import com.api.reservamed.model.Patient;
import com.api.reservamed.model.PatientRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @GetMapping
    public ResponseEntity getAll(){
        var AllPatients = repository.findAll();
        return ResponseEntity.ok().body(AllPatients);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Patient> getByCpf(@PathVariable String cpf) {
        Patient patient = repository.findByCpf(cpf);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity created(@RequestBody PatientRegistrationData data){
        Patient newPatient = new Patient(data);
        repository.save(newPatient);
        System.out.println(data);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Patient> update(@PathVariable String cpf, @RequestBody @Valid PatientRegistrationData data) {
        Patient patient = repository.findByCpf(cpf);
        if (patient != null) {
            patient.setName(data.name());
            repository.save(patient);
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{cpf}")
    public ResponseEntity delete(@PathVariable String cpf){
        Patient patient = repository.findByCpf(cpf);
        if (patient != null) {
            repository.delete(patient);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
