package com.api.reservamed.service;

import com.api.reservamed.dtos.DadosCancelamentoConsulta;
import com.api.reservamed.infra.ValidacaoException;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.DoctorsRepository;
import com.api.reservamed.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CancelamentoDeConsultas {
    @Autowired
    private DoctorsRepository medicoRepository;

    @Autowired
    private PatientRepository pacienteRepository;

    @Autowired
    private ConsultRepository consultaRepository;



    public void cancelarConsulta(DadosCancelamentoConsulta dados){
        var consulta = dados.consulta();

        if (!consultaRepository.existsById(consulta.id())) {
            throw new ValidacaoException("Id da consulta informada não existe");
        }

        if (!pacienteRepository.existsById(consulta.id_patient())) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }

        if (!medicoRepository.existsById(consulta.id_doctor())) {
            throw new ValidacaoException("Id do medico informado não existe");
        }

        if(dados.reason() == null){
            throw new ValidacaoException("O motivo não pode ser nulo");
        }

        salvarCancelamento(dados);
    }

    private void salvarCancelamento(DadosCancelamentoConsulta dados){
        try{
            var consulta = consultaRepository.getReferenceById(dados.consulta().id());
            if(consulta.getStatus().equals("C")){
                throw new ValidacaoException("A consulta já foi cancelada");
            }

            consulta.setStatus("A");
            consulta.setDate_cancellation(LocalDateTime.now());
            consulta.setCancellation_reason(dados.reason());
        }catch (Exception e){
            throw new ValidacaoException("Aconteceu um erro ao excluir consulta: " + e.getMessage());
        }
    }
}
