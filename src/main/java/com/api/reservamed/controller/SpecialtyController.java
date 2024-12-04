package com.api.reservamed.controller;

import com.api.reservamed.dtos.CreateSpecialtyDTO;
import com.api.reservamed.dtos.UpdateSpecialtyDTO;
import com.api.reservamed.model.Specialty;
import com.api.reservamed.repositories.SpecialtyRepository;
import com.api.reservamed.service.SpecialtyService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialty")
public class SpecialtyController {

    @Autowired
    private SpecialtyRepository repository;

    @Autowired
    private SpecialtyService service;

    @GetMapping
    public ResponseEntity<List<Specialty>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{specialty}")
    public ResponseEntity<Specialty> getByspecialty(@PathVariable String specialty) {
        return ResponseEntity.ok(service.getByspecialty(specialty));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Specialty> created(@RequestBody @Valid CreateSpecialtyDTO data) {
        return ResponseEntity.ok(service.create(data));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Specialty> update(@PathVariable Long id, @RequestBody @Valid UpdateSpecialtyDTO data) {
        return ResponseEntity.ok(service.update(id, data));
    }

    @Transactional
    @DeleteMapping("/{specialty}")
    public ResponseEntity<Void> deleteByspecialty(@PathVariable String specialty) {
        service.deleteByspecialty(specialty);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
