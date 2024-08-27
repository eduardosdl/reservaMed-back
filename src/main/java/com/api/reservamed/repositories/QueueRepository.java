package com.api.reservamed.repositories;

import com.api.reservamed.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {

    @Query("""
        select count(q) from queue q
        where q.doctor.id = :id_doctor
        and q.date = :date
        """)
    int posicaoFila(@Param("id_doctor") Long id_doctor, @Param("date") LocalDateTime date);
}
