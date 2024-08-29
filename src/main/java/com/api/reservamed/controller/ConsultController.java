package com.api.reservamed.controller;

import com.api.reservamed.dtos.*;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.service.AgendaDeConsultas;
import com.api.reservamed.service.CancelamentoDeConsultas;
import com.api.reservamed.service.ConfirmarConsulta;
import com.api.reservamed.service.ReagendarConsulta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ReagendarConsulta reagendarConsulta;

    @PostMapping
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/cancelamento")
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){
        cancelar.cancelarConsulta(dados);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity confirmarConsulta(@PathVariable Long id, @RequestBody DadosDiagnostic dadosDiagnostic){
        var consultPresent = consultaRepository.findById(id);
        if(consultPresent.isPresent()){
            var dataConfirm = new DadosConfirmaConsulta(
                    consultPresent.get().getId(),
                    consultPresent.get().getDoctor().getId(),
                    consultPresent.get().getPatient().getId(),
                    consultPresent.get().getType_consult(),
                    consultPresent.get().getDate(),
                    consultPresent.get().getStatus(),
                    dadosDiagnostic
                    );
            var historu = confirmarConsulta.confirmar(dataConfirm);
            return ResponseEntity.ok(historu);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/reschedule/update")
    public ResponseEntity reschedule(@Valid @RequestBody DadosReagendamentoConsulta dados){
        if(consultaRepository.findById(dados.id()).isPresent()){
            return ResponseEntity.ok(reagendarConsulta.reagendar(dados));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(consultaRepository.findAll());
    }
}
