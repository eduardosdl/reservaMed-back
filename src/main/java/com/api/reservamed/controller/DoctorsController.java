package com.api.reservamed.controller;

import com.api.reservamed.dtos.RequestDoctorDTO;
import com.api.reservamed.dtos.ResponseDoctorScheduleDTO;
import com.api.reservamed.model.Consult;
import com.api.reservamed.model.Doctor;
import com.api.reservamed.service.ConsultService;
import com.api.reservamed.service.DoctorsService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorsController {

    @Autowired
    DoctorsService service;

    @Autowired
    ConsultService consultService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAll(){
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Doctor> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/{crm}")
    public ResponseEntity<Doctor> getByCrm(@PathVariable String crm){
        return ResponseEntity.ok(service.getByCrm(crm));
    }

    @GetMapping("/{crm}/consults")
    public ResponseEntity<ResponseDoctorScheduleDTO> getConsultsByCrmAndDate (
        @PathVariable @Valid String crm,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        List<Consult> consults = consultService.getConsultsByDoctorCrmAndDate(crm, date);
        return ResponseEntity.ok(ResponseDoctorScheduleDTO.fromConsults(consults));
    }



    @Transactional
    @PostMapping
    public ResponseEntity<Doctor> create(@RequestBody @Valid RequestDoctorDTO data){
        return ResponseEntity.ok(service.create(data));
    }

    @Transactional
    @PutMapping("/{crm}")
    public ResponseEntity<Doctor> update(@PathVariable String crm, @RequestBody @Valid RequestDoctorDTO data) {
        return ResponseEntity.ok(service.update(crm, data));
    }

    @Transactional
    @DeleteMapping("/{crm}")
    public ResponseEntity<Void> delete(@PathVariable String crm) {
        service.deleteByCrm(crm);
        return ResponseEntity.ok().build();
    }
}
