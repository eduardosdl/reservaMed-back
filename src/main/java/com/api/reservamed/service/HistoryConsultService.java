package com.api.reservamed.service;

import com.api.reservamed.dtos.DadosRetornoHistoryConsult;
import com.api.reservamed.infra.exception.ValidacaoException;
import com.api.reservamed.model.Doctors;
import com.api.reservamed.model.HistoryConsult;
import com.api.reservamed.model.Patient;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.DoctorsRepository;
import com.api.reservamed.repositories.HistoryConsutRepository;
import com.api.reservamed.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryConsultService {

    @Autowired
    private HistoryConsutRepository repository;

    @Autowired
    private ConsultRepository consultRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorsRepository doctorsRepository;

    public List<DadosRetornoHistoryConsult> getAll(){
        try{
            var historyConsultReturn = repository.findAll();
            List<DadosRetornoHistoryConsult> listReturnHcr = new ArrayList<>();
            if(!historyConsultReturn.isEmpty()){
                for (HistoryConsult hcr : historyConsultReturn){
                    Optional<Patient> patient = patientRepository.findById(hcr.getId_patient());
                    Optional<Doctors> doctors = doctorsRepository.findById(hcr.getId_doctor());
                    listReturnHcr.add(new DadosRetornoHistoryConsult(hcr.getId_consult(),
                            patient.get(),
                            doctors.get(),
                            // Este método faço um operador ternário verificando se o campo é nulo
                            hcr.getType_consult() != null ? hcr.getType_consult().getDescription() : "N/A",
                            hcr.getDate(),
                            hcr.getDate_cancellation(),
                            hcr.getCancellation_reason(),
                            hcr.getStatus(),
                            hcr.getDiagnostic(),
                            hcr.getPrescription()));
                }
                return listReturnHcr;
            }

            return null;

        }catch (Exception e){
            throw new ValidacaoException("Error: " + e.getMessage());
        }
    }
}
