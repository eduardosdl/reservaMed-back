package com.api.reservamed.dtos;

import com.api.reservamed.model.Doctors;
import com.api.reservamed.model.Patient;

import java.time.LocalDateTime;


public record DadosRetornoHistoryConsult(Long id_consult,
                                         Patient patient,
                                         Doctors doctor,
                                         String type_consult,
                                         LocalDateTime date,
                                         LocalDateTime date_cancellation,
                                         String cancellation_reason,
                                         String status,
                                         String diagnostic,
                                         String prescription) {

}
