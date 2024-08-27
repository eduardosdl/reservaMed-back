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
public class DoctorsController {

    @Autowired
    DoctorsService doctorsService;

    @GetMapping("/doctors")
    public List doctors(){
        return doctorsService.listAll();
    }

    @GetMapping("/doctor/{crm}")
    public Object doctor(@PathVariable String crm){
        return doctorsService.listDoctorCrm(crm);
    }

    @Transactional
    @PostMapping("/add-doctor")
    public ResponseEntity addDorctor(@RequestBody @Valid DoctorDto data){
        return doctorsService.saveDoctor(data);
    }

    @Transactional
    @DeleteMapping("dell-doctor/{crm}")
    public void deleteDoctor(@PathVariable String crm) {
        doctorsService.dellDoctor(crm);
    }

    @Transactional
    @PutMapping("/update-doctor/{crm}")
    public Object updateDoctor(@PathVariable String crm ,@RequestBody Doctors doctor) {
        return doctorsService.updateDoctor(crm,doctor);
    }
}
