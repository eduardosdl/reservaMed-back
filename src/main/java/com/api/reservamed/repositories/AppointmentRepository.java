package com.api.reservamed.repositories;

import com.api.reservamed.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
    select case when count(c) > 0 then false else true end
                                   from consult c
                                   where c.doctor.id = :id_medico
                                   and c.date = :date
                                   and c.status not in ('C', 'P')
    """)
    boolean consultaDisponibilidadeMedicoNoHorario(@Param("id_medico") Long id_medico, @Param("date") LocalDateTime date);

    List<Appointment> findByPatientCpf(String cpf);

    List<Appointment> findByDoctorCrmAndPatientCpfAndStatus(String crm, String cpf, String status);

    @Query("SELECT COUNT(c) FROM consult c WHERE c.doctor.crm = :crm AND DATE(c.date) = DATE(:date) AND c.status = :status")
    long countByDoctorCrmAndDateAndStatus(@Param("crm") String crm, @Param("status") String status, @Param("date") LocalDateTime date);

    @Query("SELECT c FROM consult c WHERE c.doctor.crm = :crm AND DATE(c.date) = DATE(:date) AND c.status = :status")
    List<Appointment> findByDoctorCrmAndDateAndStatus(@Param("crm") String crm, @Param("status") String status, @Param("date") LocalDateTime date);

    @Query("""
            select c from consult c
            where status = 'A'
            """)
    List<Appointment> findAllActive();

    @Query("""
            select c from consult c
            where c.status in ('P', 'C')
            """)
    List<Appointment> findAllCompletedOrPendingOrCancelled();

    @Query("SELECT c.date FROM consult c WHERE c.doctor.id = :doctorId AND DATE(c.date) = :date")
    List<LocalDateTime> findBookedDateTimes(@Param("doctorId") Long doctorId, @Param("date") LocalDate date);
}
