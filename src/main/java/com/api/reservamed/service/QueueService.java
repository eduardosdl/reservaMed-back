package com.api.reservamed.service;

import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.model.Queue;
import com.api.reservamed.repositories.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

    public void insertQueue(Queue queue){
        try{
            queueRepository.save(queue);
        }catch (Exception e){
            throw new ValidationException("Houve um erro ao inserir na fila de espera");
        }
    }

    public int positionQueue(Long doctorId, LocalDateTime date){
        try {
            return queueRepository.posicaoFila(doctorId, date);
        } catch (Exception e) {
            throw new ValidationException("Houve um erro ao buscar a posição na fila de espera");
        }
    }
}
