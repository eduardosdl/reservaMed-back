package com.api.reservamed.controller;

import com.api.reservamed.dtos.DoctorDto;
import com.api.reservamed.model.Doctors;
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
    DoctorsService doctorsService;

    @GetMapping
    public ResponseEntity doctors(){
        return ResponseEntity.ok(doctorsService.listAll());
    }

    @GetMapping("/{crm}")
    public ResponseEntity<Doctors> doctor(@PathVariable String crm){

        return doctorsService.listDoctorCrm(crm);
    }

    @Transactional
    @PostMapping
    public ResponseEntity addDoctor(@RequestBody @Valid DoctorDto data){
        return doctorsService.saveDoctor(data);
    }

    @Transactional
    @DeleteMapping("/{crm}")
    public void deleteDoctor(@PathVariable String crm) {
        doctorsService.dellDoctor(crm);
    }

    @Transactional
    @PutMapping("/{crm}")
    public ResponseEntity updateDoctor(@PathVariable String crm ,@RequestBody Doctors doctor) {
        return ResponseEntity.ok(doctorsService.updateDoctor(crm,doctor));
    }
}
