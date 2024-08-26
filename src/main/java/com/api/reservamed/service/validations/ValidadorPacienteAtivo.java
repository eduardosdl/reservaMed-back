package com.api.reservamed.service.validations;


import com.api.reservamed.dtos.DadosAgendamentoConsulta;
import com.api.reservamed.infra.ValidacaoException;
import com.api.reservamed.repositories.PatientRepository;

public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta{
    private PatientRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados){
        var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.id_patient());
        if(!pacienteEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
