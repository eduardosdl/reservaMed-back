package com.api.reservamed.service;

import com.api.reservamed.dtos.DadosCancelamentoConsulta;
import com.api.reservamed.infra.ValidacaoException;
import com.api.reservamed.model.HistoryConsult;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.DoctorsRepository;
import com.api.reservamed.repositories.HistoryConsutRepository;
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

    @Autowired
    private HistoryConsutRepository historyConsutRepository;



    public void cancelarConsulta(DadosCancelamentoConsulta dados){
        var consulta = consultaRepository.getReferenceById(dados.id());

        if (!consultaRepository.existsById(consulta.getId())) {
            throw new ValidacaoException("Id da consulta informada não existe");
        }

        if (!pacienteRepository.existsById(consulta.getPatient().getId())) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }

        if (!medicoRepository.existsById(consulta.getDoctor().getId())) {
            throw new ValidacaoException("Id do medico informado não existe");
        }

        if(dados.reason() == null){
            throw new ValidacaoException("O motivo não pode ser nulo");
        }

        salvarCancelamento(dados);
    }

    private void salvarCancelamento(DadosCancelamentoConsulta dados){
        try{
            var consulta = consultaRepository.getReferenceById(dados.id());
            if(consulta.getStatus().equals("C")){
                throw new ValidacaoException("A consulta já foi cancelada");
            }

            // Verifica se a data atual é anterior à data da consulta
            if (LocalDateTime.now().isBefore(consulta.getDate())) {
                // Calcula a diferença de horas entre a data atual e a data da consulta
                long hoursDifference = java.time.Duration.between(LocalDateTime.now(), consulta.getDate()).toHours();

                // Se a diferença for menor que 24 horas, lança uma exceção
                if (hoursDifference < 24) {
                    throw new ValidacaoException("A consulta deve ser cancelada com pelo menos 24 horas de antecedência" +
                            ", caso o cancelamento seja feito será cobrado uma taxa de cancelamento!");
                }
            }else{
                throw new ValidacaoException("A consulta não pode ser cancelada pois já passou do horário agendado");
            }

            consulta.setStatus("A");
            consulta.setDate_cancellation(LocalDateTime.now());
            consulta.setCancellation_reason(dados.reason());

            var historyConsult = new HistoryConsult(dados, consulta.getDoctor().getId(), consulta.getPatient().getId(), consulta.getDate());
            historyConsutRepository.save(historyConsult);
        }catch (Exception e){
            throw new ValidacaoException("Aconteceu um erro ao excluir consulta: " + e.getMessage());
        }
    }
}
