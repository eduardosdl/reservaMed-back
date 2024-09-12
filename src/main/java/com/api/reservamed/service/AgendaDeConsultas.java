package com.api.reservamed.service;

import com.api.reservamed.dtos.DadosAgendamentoConsulta;
import com.api.reservamed.dtos.DadosDetalhamentoConsulta;
import com.api.reservamed.infra.exception.ValidacaoException;
import com.api.reservamed.model.Consult;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.DoctorsRepository;
import com.api.reservamed.repositories.PatientRepository;
import com.api.reservamed.service.validations.agendamento.ValidadorAgendamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultRepository consultaRepository;

    @Autowired
    private DoctorsRepository medicoRepository;

    @Autowired
    private PatientRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validacoes;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){

        if (!pacienteRepository.existsByCpf(dados.cpf_patient())) {
            throw new ValidacaoException("CPF do paciente informado não existe");
        }

        if (dados.id_doctor()!= null && !medicoRepository.existsById(dados.id_doctor())) {
            throw new ValidacaoException("Id do medico informado não existe");
        }

        validacoes.forEach(v -> v.validar(dados));

        // Irá entrar aqui se todas as validações passarem
        var consultaRetorno = salvarConsulta(dados);
        return new DadosDetalhamentoConsulta(consultaRetorno.getId(), dados.id_doctor(), dados.cpf_patient(), dados.date());
    }

    private Consult salvarConsulta(DadosAgendamentoConsulta dados) {
        var medico = medicoRepository.getReferenceById(dados.id_doctor());
        var paciente = pacienteRepository.findByCpf(dados.cpf_patient());
        var consulta = new Consult(medico, paciente, dados.date(), dados.type());
        return consultaRepository.save(consulta);
    }
}

