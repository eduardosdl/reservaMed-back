package com.api.reservamed.controller;

import com.api.reservamed.model.Consult;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.HistoryConsutRepository;
import org.aspectj.apache.bcel.generic.LocalVariableGen;
import org.flywaydb.core.internal.reports.ReportDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/historyConsult")
public class DataConsultController {
    @Autowired
    private HistoryConsutRepository repository;

    @Autowired
    private ConsultRepository consultRepository;

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(consultRepository.findByStatusNot("A"));
    }

    @GetMapping("/{id}")
    public ResponseEntity getAllByPaciente(@PathVariable java.lang.Long id){
        return ResponseEntity.ok(repository.findAllByIdPatient(id));
    }

    @GetMapping("/consult/{id}")
    public ResponseEntity<Object> getByConsult(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findByIdConsult(id));
    }

    @GetMapping("/prescription/{id}")
    public ResponseEntity closed(@PathVariable Long id){
        try{
            var prescription = repository.findPrescriptionById(id);
            var history_consult = repository.findByIdConsult(id);
            if(history_consult.isPresent() && history_consult.get().getStatus().equals("P")){
                if(prescription.isPresent()){
                    return ResponseEntity.ok(prescription);
                }
                return ResponseEntity.ok("Prescription not found in this consult or the consult not processed!");
            }
            return ResponseEntity.notFound().build();

        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
