package com.api.reservamed.service;

import com.api.reservamed.dtos.DadosDetalhamentoConsulta;
import com.api.reservamed.dtos.DadosReagendamentoConsulta;
import com.api.reservamed.infra.ValidacaoException;
import com.api.reservamed.model.Consult;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.DoctorsRepository;
import com.api.reservamed.repositories.PatientRepository;
import com.api.reservamed.service.validations.reagendamento.ValidadorReagendamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReagendarConsulta {
    @Autowired
    private ConsultRepository consultaRepository;

    @Autowired
    private DoctorsRepository medicoRepository;

    @Autowired
    private PatientRepository pacienteRepository;

    @Autowired
    private List<ValidadorReagendamentoDeConsulta> validacoes;

    public ResponseEntity reagendar(DadosReagendamentoConsulta dados){

        if (!pacienteRepository.existsByCpf(dados.cpf_patient())) {
            throw new ValidacaoException("CPF do paciente informado não existe");
        }

        if (dados.id_doctor()!= null && !medicoRepository.existsById(dados.id_doctor())) {
            throw new ValidacaoException("Id do medico informado não existe");
        }

        var consulta = consultaRepository.getReferenceById(dados.id());
        if(consulta.getStatus().equals("C")){
            throw new ValidacaoException("A consulta já foi cancelada");
        }
        // Calcula a diferença de horas entre a data atual e a data da consulta
        long hoursDifference = java.time.Duration.between(LocalDateTime.now(), consulta.getDate()).toHours();

        // Se a diferença for menor que 24 horas, lança uma exceção
        if (hoursDifference < 24) {
            salvarConsulta(dados);
            return ResponseEntity.ok("A consulta deve ser cancelada com pelo menos 24 horas de antecedência, será cobrado uma taxa de cancelamento!");
        }

        validacoes.forEach(v -> v.validar(dados));

        // Irá entrar aqui se todas as validações passarem
        var consultaRetorno = salvarConsulta(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consultaRetorno.getId(), dados.id_doctor(), dados.cpf_patient(), dados.date()));
    }

    private Consult salvarConsulta(DadosReagendamentoConsulta dados) {
        var medico = medicoRepository.getReferenceById(dados.id_doctor());
        var paciente = pacienteRepository.findByCpf(dados.cpf_patient());
        var consulta = new Consult(medico, paciente, dados.date(), dados.type());
        consulta.setId(dados.id());
        return consultaRepository.save(consulta);
    }
}


