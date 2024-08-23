package com.api.reservamed.service;

import com.api.reservamed.model.Doctors;
import com.api.reservamed.repositories.DoctorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void dellDoctor(String crm) {
        doctorsRepository.deleteByCrm(crm);
    }

    public Optional<Doctors> updateDoctor(String crm, Doctors doctor) {
        Optional<Doctors> doctorCrm = Optional.ofNullable(doctorsRepository.findByCrm(crm));
        doctorCrm = Optional.of(doctorsRepository.save(doctor));
        return doctorCrm;
    }


}


