package com.api.reservamed.service;

import com.api.reservamed.dtos.DadosConfirmaConsulta;
import com.api.reservamed.dtos.DadosDetalhamentoConsulta;
import com.api.reservamed.dtos.DadosDiagnostic;
import com.api.reservamed.infra.ValidacaoException;
import com.api.reservamed.model.Consult;
import com.api.reservamed.model.HistoryConsult;
import com.api.reservamed.repositories.ConsultRepository;
import com.api.reservamed.repositories.HistoryConsutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmarConsulta {

    @Autowired
    private ConsultRepository repository;

    @Autowired
    private HistoryConsutRepository historyConsutRepository;

    public DadosConfirmaConsulta confirmar(Long id, DadosDiagnostic dadosDiagnostic){
        var consulta = repository.getReferenceById(id);
        var dadosConfirmaConsulta = new DadosConfirmaConsulta(consulta, dadosDiagnostic);
        if(consulta.getId() != null){
            if(consulta.getStatus().equals("C")){
                throw new ValidacaoException("A consulta já foi cancelada, não é possível alterar para processada");
            }
            finalizarConsulta(consulta);
            inserirHistoricoConsulta(dadosConfirmaConsulta);
        }
        return dadosConfirmaConsulta;
    }

    public void inserirHistoricoConsulta(DadosConfirmaConsulta dados){
        var history = new HistoryConsult(dados);
        historyConsutRepository.save(history);
    }

    public void finalizarConsulta(Consult consult){
        consult.setStatus("P");
        repository.save(consult);
    }
}
