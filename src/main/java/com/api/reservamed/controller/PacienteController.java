package com.api.reservamed.controller;

import com.api.reservamed.dtos.DadosCadastroPaciente;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @PostMapping
    public ResponseEntity cadastrarPaciente(@RequestBody DadosCadastroPaciente dados){
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity alterarPaciente(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/:id")
    public ResponseEntity pegarPaciente(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deletarPaciente(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }
}
