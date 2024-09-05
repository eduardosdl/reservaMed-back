package com.api.reservamed.service;

import com.api.reservamed.dtos.DoctorDto;
import com.api.reservamed.infra.exception.ValidacaoException;
import com.api.reservamed.model.Doctors;
import com.api.reservamed.repositories.DoctorsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorsService {
    @Autowired
    DoctorsRepository doctorsRepository;

    public List<Doctors> listAll(){
        try {
            return doctorsRepository.findAll();
        }
        catch (Exception e){
            throw new ValidacaoException("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<Doctors> listDoctorCrm(String crm){

        Doctors doctor = doctorsRepository.findByCrm(crm);
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity saveDoctor(DoctorDto doctor){
        Doctors newDoctor = new Doctors();

        if(doctorsRepository.findByCrm(doctor.crm()) != null){
            return ResponseEntity.badRequest().body("CRM já existe");
        }

        if(doctorsRepository.findByCellPhone(doctor.cellPhone()) != null){
            return ResponseEntity.badRequest().body("Telefone ja registrado");
        }

        newDoctor.setName(doctor.name());
        newDoctor.setCrm(doctor.crm());
        newDoctor.setSpecialty(doctor.specialty());
        newDoctor.setCellPhone(doctor.cellPhone());
        newDoctor.setActive(true);
        return ResponseEntity.ok(doctorsRepository.save(newDoctor));
    }
    @Transactional
    public ResponseEntity<Object> dellDoctor(String crm) {

        Doctors doctors = doctorsRepository.findByCrm(crm);

        if (doctors != null) {
            doctorsRepository.delete(doctors);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Transactional
    public Doctors updateDoctor(String crm, Doctors doctor) {

            Doctors doctors = doctorsRepository.findByCrm(crm);

            if (doctor != null && doctors != null) {
                if (doctor.getCellPhone() != null) {
                    doctors.setCellPhone(doctor.getCellPhone());
                }
                if (doctor.getCrm() != null) {
                    doctors.setCrm(doctor.getCrm());
                }
                if (doctor.getName() != null) {
                    doctors.setName(doctor.getName());
                }
                if (doctor.getSpecialty() != null) {
                    doctors.setSpecialty(doctor.getSpecialty());
                }
                if(doctor.getActive() != null) {
                    doctors.setActive(doctor.getActive());
                }

                return doctorsRepository.save(doctors);
            } else {
                throw new ValidacaoException("User not found");
            }
        }
    }





