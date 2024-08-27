package com.api.reservamed.repositories;

import com.api.reservamed.model.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {

    @Query("""
    select case when count(c) > 0 then false else true end
    from consult c
    where c.doctor.id = :id_medico
    and c.date = :date
""")
    boolean consultaDisponibilidadeMedicoNoHorario(@Param("id_medico") Long id_medico, @Param("date") LocalDateTime date);

    List<Consult> findAllById(Long id);
}
