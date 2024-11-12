package com.api.reservamed.controller;

import com.api.reservamed.dtos.RequestDoctorDTO;
import com.api.reservamed.dtos.ResponseDoctorScheduleDTO;
import com.api.reservamed.model.Appointment;
import com.api.reservamed.model.Doctor;
import com.api.reservamed.service.AppointmentService;
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
    AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Doctor> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/{crm}")
    public ResponseEntity<Doctor> getByCrm(@PathVariable String crm) {
        return ResponseEntity.ok(service.getByCrm(crm));
    }

    @GetMapping("/{crm}/appointments")
    public ResponseEntity<ResponseDoctorScheduleDTO> getAppointmentsByCrmAndDate(
            @PathVariable @Valid String crm,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        if (date == null) {
            date = LocalDateTime.now();
        }

        if (status == null) {
            status = "A";
        }

        List<Appointment> appointments = appointmentService.getAppointmentByDoctorCrmAndStatusAndDate(crm, status, date);
        long attendedCount = appointmentService.countAppointmentByDoctorCrmAndStatusAndDate(crm, "P", date);
        long pendingCount = appointmentService.countAppointmentByDoctorCrmAndStatusAndDate(crm, "A", date);

        return ResponseEntity.ok(new ResponseDoctorScheduleDTO(
                attendedCount,
                pendingCount,
                appointments
        ));
    }

    @GetMapping("/{crm}/patient/{cpf}/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentByDoctorCrmAndPatientCpf(@PathVariable String crm, @PathVariable String cpf) {
        return ResponseEntity.ok(appointmentService.getAppointmentCompletedByDoctorCrmAndPatientCpf(crm, cpf));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Doctor> create(@RequestBody @Valid RequestDoctorDTO data) {
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
