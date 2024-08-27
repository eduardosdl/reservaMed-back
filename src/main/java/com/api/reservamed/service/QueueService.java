package com.api.reservamed.service;

import com.api.reservamed.infra.ValidacaoException;
import com.api.reservamed.model.Queue;
import com.api.reservamed.repositories.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

    public void insertQueue(Queue queue){
        try{
            queueRepository.save(queue);
        }catch (Exception e){
            throw new ValidacaoException("Error: " + e.getMessage());
        }

    }
}
