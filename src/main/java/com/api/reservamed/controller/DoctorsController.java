package com.api.reservamed.controller;

import com.api.reservamed.dtos.RequestDoctorDTO;
import com.api.reservamed.model.Doctor;
import com.api.reservamed.service.DoctorsService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorsController {

    @Autowired
    DoctorsService service;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAll(){
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{crm}")
    public ResponseEntity<Doctor> getByCrm(@PathVariable String crm){
        return ResponseEntity.ok(service.getByCrm(crm));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Doctor> create(@RequestBody @Valid RequestDoctorDTO data){
        return ResponseEntity.ok(service.create(data));
    }

//    @Transactional
//    @DeleteMapping("/{crm}")
//    public void deleteDoctor(@PathVariable String crm) {
//        service.dellDoctor(crm);
//    }

//    @Transactional
//    @PutMapping("/{crm}")
//    public ResponseEntity updateDoctor(@PathVariable String crm ,@RequestBody Doctors doctor) {
//        return ResponseEntity.ok(service.updateDoctor(crm,doctor));
//    }
}
