package com.api.reservamed.controller;

import com.api.reservamed.dtos.DadosAgendamentoConsulta;
import com.api.reservamed.dtos.DadosCancelamentoConsulta;
import com.api.reservamed.dtos.DadosDiagnostic;
import com.api.reservamed.dtos.PatientRegistrationData;
import com.api.reservamed.model.Consult;
import com.api.reservamed.model.Patient;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.service.AgendaDeConsultas;
import com.api.reservamed.service.CancelamentoDeConsultas;
import com.api.reservamed.service.ConfirmarConsulta;
import com.api.reservamed.service.ConsultService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consults")
public class ConsultController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultRepository consultaRepository;

    @Autowired
    private CancelamentoDeConsultas cancelar;

    @Autowired
    private ConfirmarConsulta confirmarConsulta;

    @Autowired
    private ConsultService consultService;

    @GetMapping
    public ResponseEntity getAllConsults() {
        Iterable<Consult> consults = consultaRepository.findAll();
        return ResponseEntity.ok(consults);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity getByCpf(@PathVariable String cpf) {
        var consults = consultService.getConsultsByPatientCpf(cpf);
        return ResponseEntity.ok(consults);
    }


    @PostMapping
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){
        cancelar.cancelarConsulta(dados);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity confirmarConsulta(@PathVariable Long id, @RequestBody DadosDiagnostic dadosDiagnostic){
        var historu = confirmarConsulta.confirmar(id, dadosDiagnostic);
        return ResponseEntity.ok(historu);
    }
}
