package com.api.reservamed.controller;

import com.api.reservamed.dtos.PatientRegistrationData;
import com.api.reservamed.dtos.PatientUpdateData;
import com.api.reservamed.model.Patient;
import com.api.reservamed.repositories.PatientRepository;
import com.api.reservamed.service.PatientService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.ok(repository.findAll());
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

    @Transactional
    @PostMapping
    public ResponseEntity<Object> created(@RequestBody @Valid PatientRegistrationData data) {
        try{
            if (repository.existsByCpf(data.cpf())) {
                return ResponseEntity.badRequest().body("CPF já existe no banco de dados");
            }

            Patient newPatient = new Patient(data);
            var patient = repository.save(newPatient);
            return ResponseEntity.status(HttpStatus.CREATED).body(patient); // 201 Created
        }catch (Exception e){
            throw new ValidationException("Aconteceu algum erro ao registrar um paciente: " + e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/{cpf}")
    public ResponseEntity<Object> update(@PathVariable String cpf, @RequestBody @Valid PatientUpdateData data) {
        Patient patient = repository.findByCpf(cpf);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        if (data.cpf() != null && (!data.cpf().equals(cpf)) && repository.existsByCpf(data.cpf())) {
            return ResponseEntity.badRequest().body("CPF já existe no banco de dados");
        }

        return ResponseEntity.ok(service.updatePatient(data, cpf));
    }

    @Transactional
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable String cpf) {
        Patient patient = repository.findByCpf(cpf);
        if (patient != null) {
            repository.delete(patient);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
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
