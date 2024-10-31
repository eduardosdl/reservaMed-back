package com.api.reservamed.service;

import com.api.reservamed.dtos.RequestDoctorDTO;
import com.api.reservamed.infra.exception.ValidationException;
import com.api.reservamed.model.Doctor;
import com.api.reservamed.repositories.DoctorsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorsService {
    @Autowired
    DoctorsRepository repository;

    public List<Doctor> listAll(){
        try {
            return repository.findAll();
        }
        catch (Exception e){
            throw new RuntimeException("Houve um erro ao buscar médicos");
        }
    }

    public Doctor getByCrm(String crm){
        try {
            return repository.findByCrm(crm).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao buscar CRM");
        }
    }

    public Doctor create(RequestDoctorDTO doctorData){
        try {
            validateCrm(doctorData.crm());
            validateCellPhone(doctorData.cellPhone());

            Doctor newDoctor = new Doctor(doctorData);

            return repository.save(newDoctor);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Houve um erro ao cadastrar médico");
        }
    }

    // falta adicionar atualização e exclusão

    private void validateCrm(String crm) {
        if (crm == null) throw new ValidationException("Informe um CRM");

        repository.findByCrm(crm).ifPresent(existingDoctor -> {
                throw new ValidationException("CRM já cadastrado");
        });
    }

    private void validateCellPhone(String phoneNumber) {
        if (phoneNumber == null) throw new ValidationException("Informe um número de telefone");

        repository.findByCellPhone(phoneNumber).ifPresent(existingDoctor -> {
            throw new ValidationException("Número de telefone ja cadastrado");
        });
    }

//    @Transactional
//    public ResponseEntity<Object> dellDoctor(String crm) {
//
//        Doctors doctors = doctorsRepository.findByCrm(crm);
//
//        if (doctors != null) {
//            doctorsRepository.delete(doctors);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//    @Transactional
//    public Doctors updateDoctor(String crm, Doctors doctor) {
//
//            Doctors doctors = doctorsRepository.findByCrm(crm);
//
//            if (doctor != null && doctors != null) {
//                if (doctor.getCellPhone() != null) {
//                    doctors.setCellPhone(doctor.getCellPhone());
//                }
//                if (doctor.getCrm() != null) {
//                    doctors.setCrm(doctor.getCrm());
//                }
//                if (doctor.getName() != null) {
//                    doctors.setName(doctor.getName());
//                }
//                if (doctor.getSpecialty() != null) {
//                    doctors.setSpecialty(doctor.getSpecialty());
//                }
//                if(doctor.getActive() != null) {
//                    doctors.setActive(doctor.getActive());
//                }
//
//                return doctorsRepository.save(doctors);
//            } else {
//                throw new ValidationException("User not found");
//            }
//        }
    }





