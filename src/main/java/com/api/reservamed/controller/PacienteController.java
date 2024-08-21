package com.api.reservamed.controller;

import com.api.reservamed.dtos.DadosCadastroPaciente;
import com.api.reservamed.model.Paciente;
import com.api.reservamed.model.PacienteRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

//    @GetMapping("/:id")
//    public ResponseEntity pegarPaciente(@PathVariable Long id){
//        return ResponseEntity.ok().build();
//    }
    @Autowired
    private PacienteRepository repository;

    @GetMapping
    public ResponseEntity pegarTodosPaciente(){
        var TodosPacientes = repository.findAll();
        return ResponseEntity.ok().body(TodosPacientes);
    }

    @PostMapping
    public ResponseEntity cadastrarPaciente(@RequestBody DadosCadastroPaciente dados){
        Paciente novoPaciente = new Paciente(dados);
        repository.save(novoPaciente);
        System.out.println(dados);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity alterarPaciente(){
        return ResponseEntity.ok().build();
    }


    @DeleteMapping
    public ResponseEntity deletarPaciente(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }
}
