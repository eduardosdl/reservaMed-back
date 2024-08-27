package com.api.reservamed.repositories;

import com.api.reservamed.model.HistoryConsult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryConsutRepository extends JpaRepository<HistoryConsult, Long> {
    @Query("""
        select h from history_consult h
        where h.id_patient = :id
        """)
    List<HistoryConsult> findAllByIdPatient(@Param("id") Long id);
}
