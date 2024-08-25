package com.api.reservamed.controller;

import com.api.reservamed.dtos.DadosAgendamentoConsulta;
import com.api.reservamed.dtos.DadosCancelamentoConsulta;
import com.api.reservamed.dtos.PatientRegistrationData;
import com.api.reservamed.model.Patient;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.service.AgendaDeConsultas;
import com.api.reservamed.service.CancelamentoDeConsultas;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consult")
public class ConsultController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultRepository consultaRepository;

    @Autowired
    private CancelamentoDeConsultas cancelar;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/cancelamento")
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){
        cancelar.cancelarConsulta(dados);
        return ResponseEntity.ok().build();
    }
}
