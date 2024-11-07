package com.api.reservamed.dtos;

import com.api.reservamed.model.Consult;

import java.util.List;

public record ResponseDoctorScheduleDTO(Integer attended, Integer pending, List<Consult> data) {
    public static ResponseDoctorScheduleDTO fromConsults(List<Consult> consults) {
        int attended = (int) consults.stream().filter(c -> "P".equals(c.getStatus())).count();
        int pending = (int) consults.stream().filter(c -> "A".equals(c.getStatus())).count();
        return new ResponseDoctorScheduleDTO(attended, pending, consults);
    }
}
