package com.ehrapp.ehr_backend.service.impl;

import com.ehrapp.ehr_backend.dto.DoctorDTO;
import com.ehrapp.ehr_backend.entity.Doctor;
import com.ehrapp.ehr_backend.repository.DoctorRepository;
import com.ehrapp.ehr_backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public DoctorDTO createDoctor(DoctorDTO dto) {
        Doctor saved = doctorRepository.save(mapToEntity(dto));
        return mapToDTO(saved);
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.map(this::mapToDTO).orElse(null);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO dto) {
        Optional<Doctor> optional = doctorRepository.findById(id);
        if (optional.isPresent()) {
            Doctor doctor = optional.get();
            doctor.setName(dto.getName());
            doctor.setSpecialization(dto.getSpecialization());
            doctor.setAvailability(dto.getAvailability());
            return mapToDTO(doctorRepository.save(doctor));
        }
        return null;
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    private DoctorDTO mapToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setAvailability(doctor.getAvailability());
        return dto;
    }

    private Doctor mapToEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setAvailability(dto.getAvailability());
        return doctor;
    }
}
