package com.api.reservamed.service.appointment;

import com.api.reservamed.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ScheduleAvailabilityService {
    @Autowired
    private AppointmentRepository repository;

    public List<String> execute(Long doctorId, LocalDate date) {
        // Gera uma lista com todos os horários possíveis no formato "HH:mm"
        List<String> allTimes = getAllPossibleTimes();

        // Busca os horários já reservados para o médico na data especificada
        List<LocalDateTime> bookedDateTimes = repository.findBookedDateTimes(doctorId, date);

        // Converte os horários reservados para o formato "HH:mm"
        List<String> bookedTimes = bookedDateTimes.stream()
                .map(dt -> dt.toLocalTime().toString())
                .toList();

        // Filtra os horários disponíveis
        return allTimes.stream()
                .filter(time -> !bookedTimes.contains(time))
                .collect(Collectors.toList());
    }

    private List<String> getAllPossibleTimes() {
        // Gera uma lista de horários possíveis (exemplo: 08:00 até 17:00 com intervalos de 30 minutos)
        return IntStream.range(8, 18)
                .mapToObj(hour -> List.of(
                        String.format("%02d:00", hour),
                        String.format("%02d:30", hour)))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
