package com.api.reservamed.service.validations.agendamento;

import com.api.reservamed.dtos.DadosAgendamentoConsulta;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.model.Queue;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.DoctorsRepository;
import com.api.reservamed.repositories.PatientRepository;
import com.api.reservamed.repositories.QueueRepository;
import com.api.reservamed.service.PatientService;
import com.api.reservamed.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoLivreNoHorario implements ValidadorAgendamentoDeConsulta{
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

    @Autowired
    private PatientService patientService;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        if((!repository.consultaDisponibilidadeMedicoNoHorario(dados.id_doctor(), dados.date()))){
            insertQueue(dados);
            throw new ValidationException("Medico não está disponível no horário alocado! Mas foi colocado na fila de espera. ");
        }
    }

    private void insertQueue(DadosAgendamentoConsulta dados){
        try {
            var queuePosition = queueRepository.posicaoFila(dados.id_doctor(), dados.date());
            queuePosition += 1;
            var doctor = doctorsRepository.findById(dados.id_doctor());
            var patient = patientService.getByCpf(dados.cpf_patient());
            if (doctor.isEmpty()) {
                throw new ValidationException("Doctor don´t found. ");
            }
            var queue = new Queue(doctor.get(), patient, dados.date(), dados.type());
            queue.setQueue_position(queuePosition);

            queueService.insertQueue(queue);
        }catch (Exception e){
            throw new ValidationException("Error: " + e.getMessage());
        }
    }
}
