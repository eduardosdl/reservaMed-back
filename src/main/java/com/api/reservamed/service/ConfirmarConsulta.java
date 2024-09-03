package com.api.reservamed.service;

import com.api.reservamed.dtos.DadosConfirmaConsulta;
import com.api.reservamed.infra.ValidacaoException;
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

    public DadosConfirmaConsulta confirmar(DadosConfirmaConsulta dadosConfirmaConsulta){
        if(dadosConfirmaConsulta.status().equals("C")){
            throw new ValidacaoException("A consulta já foi cancelada, não é possível alterar para processada");
        }
        finalizarConsulta(dadosConfirmaConsulta);
        inserirHistoricoConsulta(dadosConfirmaConsulta);
        return dadosConfirmaConsulta;
    }

    public void inserirHistoricoConsulta(DadosConfirmaConsulta dados){
        var history = new HistoryConsult(dados);
        history.setStatus("P");
        historyConsutRepository.save(history);
    }

    public void finalizarConsulta(DadosConfirmaConsulta dados){
        var consult = repository.getReferenceById(dados.id());
        consult.setStatus("P");
        repository.save(consult);
    }
}
