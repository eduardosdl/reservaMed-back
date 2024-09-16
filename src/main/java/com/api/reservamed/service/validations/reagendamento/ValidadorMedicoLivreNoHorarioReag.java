package com.api.reservamed.service.validations.reagendamento;

import com.api.reservamed.dtos.DadosReagendamentoConsulta;
import com.api.reservamed.infra.exception.ValidacaoException;
import com.api.reservamed.model.Queue;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.DoctorsRepository;
import com.api.reservamed.repositories.PatientRepository;
import com.api.reservamed.repositories.QueueRepository;
import com.api.reservamed.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoLivreNoHorarioReag implements ValidadorReagendamentoDeConsulta {
    @Autowired
    private ConsultRepository repository;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private DoctorsRepository doctorsRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private QueueService queueService;

    @Override
    public void validar(DadosReagendamentoConsulta dados) {
        if(!(repository.getReferenceById(dados.id()).getDate().equals(dados.date()))){
            if((!repository.consultaDisponibilidadeMedicoNoHorario(dados.id_doctor(), dados.date()))){
                insertQueue(dados);
                throw new ValidacaoException("Medico não está disponível no horário alocado! Mas foi colocado na fila de espera. ");
            }
        }
    }

    private void insertQueue(DadosReagendamentoConsulta dados){
        try {
            var queuePosition = queueRepository.posicaoFila(dados.id_doctor(), dados.date());
            queuePosition += 1;
            var doctor = doctorsRepository.findById(dados.id_doctor());
            var patient = patientRepository.findByCpf(dados.cpf_patient());
            if (doctor.isEmpty()) {
                throw new ValidacaoException("Doctor don´t found. ");
            }
            if (patient== null) {
                throw new ValidacaoException("Patient don´t found. ");
            }
            var queue = new Queue(doctor.get(), patient, dados.date(), dados.type());
            queue.setQueue_position(queuePosition);

            queueService.insertQueue(queue);
        }catch (Exception e){
            throw new ValidacaoException("Error: " + e.getMessage());
        }
    }
}
