package com.api.reservamed.controller;

import com.api.reservamed.dtos.RequestAppointmentDTO;
import com.api.reservamed.dtos.RequestCancelAppointmentDTO;
import com.api.reservamed.dtos.RequestCompleteAppointmentDTO;
import com.api.reservamed.model.Appointment;
import com.api.reservamed.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAll() {
        return ResponseEntity.ok(appointmentService.listAllPending());
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<List<Appointment>> getByCpf(@PathVariable String cpf) {
        var consults = appointmentService.getAppointmentByPatientCpf(cpf);
        return ResponseEntity.ok(consults);
    }

    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody @Valid RequestAppointmentDTO data) {
        var consult = appointmentService.scheduleAppointment(data);
        return ResponseEntity.ok(consult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@PathVariable Long id, @RequestBody @Valid RequestAppointmentDTO data) {
        var consult = appointmentService.rescheduleAppointment(id, data);
        return ResponseEntity.ok(consult);
    }

    @PatchMapping("/complete/{id}")
    public ResponseEntity<Appointment> complete(@PathVariable Long id, @RequestBody @Valid RequestCompleteAppointmentDTO data) {
        appointmentService.completeAppointment(id, data);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Appointment> delete(@PathVariable Long id, @RequestBody @Valid RequestCancelAppointmentDTO data) {
        appointmentService.cancelAppointment(id, data);
        return ResponseEntity.noContent().build();
    }
}
