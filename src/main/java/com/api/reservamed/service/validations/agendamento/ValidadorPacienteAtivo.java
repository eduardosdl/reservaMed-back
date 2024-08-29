package com.api.reservamed.service.validations.agendamento;


import com.api.reservamed.dtos.DadosAgendamentoConsulta;
import com.api.reservamed.infra.ValidacaoException;
import com.api.reservamed.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private PatientRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados){
        var pacienteEstaAtivo = pacienteRepository.findActiveByCpf(dados.cpf_patient());
        if(!pacienteEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
