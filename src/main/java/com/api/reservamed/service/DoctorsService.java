package com.api.reservamed.service;

import com.api.reservamed.model.Doctors;
import com.api.reservamed.model.Patient;
import com.api.reservamed.repositories.DoctorsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorsService {
    @Autowired
    DoctorsRepository doctorsRepository;

    public List<Doctors> listAll(){
        return doctorsRepository.findAll();
    }

    public Object listDoctorCrm(String crm){
        return doctorsRepository.findByCrm(crm);
    }

    public Doctors saveDoctor(Doctors doctor){
        return doctorsRepository.save(doctor);
    }
    @Transactional
    public void dellDoctor(String crm) {
       doctorsRepository.deleteByCrm(crm);
    }
    @Transactional
    public Optional<Doctors> updateDoctor(String crm, Doctors doctor) {
        /*Optional<Doctors> doctorCrm = Optional.ofNullable(doctorsRepository.findByCrm(crm));
        doctorCrm = Optional.of(doctorsRepository.save(doctor));
        return doctorCrm;*/

            Doctors doctors = doctorsRepository.findByCrm(crm);

            if (doctor != null) {
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
                return Optional.of(doctorsRepository.save(doctors));
            } else {
                throw new RuntimeException("User not found");
            }
        }
    }





