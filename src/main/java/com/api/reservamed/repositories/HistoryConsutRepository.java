package com.api.reservamed.repositories;

import com.api.reservamed.model.HistoryConsult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryConsutRepository extends JpaRepository<HistoryConsult, Long> {
    List<HistoryConsult> findAllById(Long id);
}
