package com.api.reservamed.controller;

import com.api.reservamed.dtos.PatientRegistrationData;
import com.api.reservamed.model.Patient;
import com.api.reservamed.model.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

//    @GetMapping("/:id")
//    public ResponseEntity getPatient(@PathVariable Long id){
//        return ResponseEntity.ok().build();
//    }
    @Autowired
    private PatientRepository repository;

    @GetMapping
    public ResponseEntity getAll(){
        var AllPatients = repository.findAll();
        return ResponseEntity.ok().body(AllPatients);
    }

    @PostMapping
    public ResponseEntity created(@RequestBody PatientRegistrationData data){
        Patient newPatient = new Patient(data);
        repository.save(newPatient);
        System.out.println(data);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity update(){
        return ResponseEntity.ok().build();
    }


    @DeleteMapping
    public ResponseEntity delete(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }
}
