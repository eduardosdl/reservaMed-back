package com.api.reservamed.dtos;

import com.api.reservamed.model.Appointment;

import java.util.List;

public record ResponseDoctorScheduleDTO(Long attended, Long pending, List<Appointment> data) {
}
