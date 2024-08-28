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
    public List doctors(){
        return doctorsService.listAll();
    }

    @GetMapping("/{crm}")
    public Object doctor(@PathVariable String crm){
        return doctorsService.listDoctorCrm(crm);
    }

    @Transactional
    @PostMapping
    public ResponseEntity addDorctor(@RequestBody @Valid DoctorDto data){
        return doctorsService.saveDoctor(data);
    }

    @Transactional
    @DeleteMapping("/{crm}")
    public void deleteDoctor(@PathVariable String crm) {
        doctorsService.dellDoctor(crm);
    }

    @Transactional
    @PutMapping("/{crm}")
    public Object updateDoctor(@PathVariable String crm ,@RequestBody Doctors doctor) {
        return doctorsService.updateDoctor(crm,doctor);
    }
}
