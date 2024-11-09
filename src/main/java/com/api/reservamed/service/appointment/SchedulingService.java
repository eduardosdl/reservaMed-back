package com.api.reservamed.service.appointment;

import com.api.reservamed.dtos.RequestAppointmentDTO;
import com.api.reservamed.model.Appointment;
import com.api.reservamed.service.DoctorsService;
import com.api.reservamed.service.PatientService;
import com.api.reservamed.service.validations.scheduling.ValidationAppointmentScheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchedulingService {
    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorsService doctorService;

    @Autowired
    private List<ValidationAppointmentScheduling> validations;

    public Appointment execute(RequestAppointmentDTO appointmentData) {
        var patient = patientService.getByCpf(appointmentData.patientCpf());
        var doctor = doctorService.getById(appointmentData.doctorId());

        validations.forEach(v -> v.validate(appointmentData));

        return new Appointment(doctor, patient, appointmentData.date(), appointmentData.type());
    }
}
