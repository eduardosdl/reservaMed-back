package com.api.reservamed.service.validations.scheduling;

import com.api.reservamed.dtos.RequestAppointmentDTO;

public interface ValidationAppointmentScheduling {
    void validate(RequestAppointmentDTO data);
}
