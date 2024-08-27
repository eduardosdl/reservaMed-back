package com.api.reservamed.controller;

import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.HistoryConsutRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/historyConsult")
public class DataConsultController {
    private HistoryConsutRepository repository;

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getAllByPaciente(@PathVariable java.lang.Long id){
        return ResponseEntity.ok(repository.findAllByIdPatient(id));
    }
}
