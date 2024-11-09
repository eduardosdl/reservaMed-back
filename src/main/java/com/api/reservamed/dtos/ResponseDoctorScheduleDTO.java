package com.api.reservamed.dtos;

import com.api.reservamed.model.Appointment;

import java.util.List;

public record ResponseDoctorScheduleDTO(Integer attended, Integer pending, List<Appointment> data) {
    public static ResponseDoctorScheduleDTO fromConsults(List<Appointment> appointments) {
        int attended = (int) appointments.stream().filter(c -> "P".equals(c.getStatus())).count();
        int pending = (int) appointments.stream().filter(c -> "A".equals(c.getStatus())).count();
        return new ResponseDoctorScheduleDTO(attended, pending, appointments);
    }
}
