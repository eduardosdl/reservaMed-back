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
import org.springframework.stereotype.Service;

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

    public DadosDetalhamentoConsulta reagendar(DadosReagendamentoConsulta dados){

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

    private Consult salvarConsulta(DadosReagendamentoConsulta dados) {
        var medico = medicoRepository.getReferenceById(dados.id_doctor());
        var paciente = pacienteRepository.findByCpf(dados.cpf_patient());
        var consulta = new Consult(medico, paciente, dados.date(), dados.type());
        consulta.setId(dados.id());
        return consultaRepository.save(consulta);
    }
}


