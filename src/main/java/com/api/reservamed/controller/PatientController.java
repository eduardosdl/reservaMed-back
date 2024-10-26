package com.api.reservamed.controller;

import com.api.reservamed.dtos.CreatePatientDTO;
import com.api.reservamed.dtos.UpdatePatientDTO;
import com.api.reservamed.model.Patient;
import com.api.reservamed.repositories.PatientRepository;
import com.api.reservamed.service.PatientService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @Autowired
    private PatientService service;

    @GetMapping
    public ResponseEntity<List<Patient>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Patient> getByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(service.getByCpf(cpf));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Patient> created(@RequestBody @Valid CreatePatientDTO data) {
        return ResponseEntity.ok(service.create(data));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Long id, @RequestBody @Valid UpdatePatientDTO data) {
        return ResponseEntity.ok(service.update(id, data));
    }

    @Transactional
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteByCpf(@PathVariable String cpf) {
        service.deleteByCpf(cpf);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
