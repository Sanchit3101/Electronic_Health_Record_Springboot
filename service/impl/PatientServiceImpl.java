package com.ehrapp.ehr_backend.service.impl;

import com.ehrapp.ehr_backend.dto.PatientDTO;
import com.ehrapp.ehr_backend.entity.Patient;
import com.ehrapp.ehr_backend.repository.PatientRepository;
import com.ehrapp.ehr_backend.service.PatientService;
import com.ehrapp.ehr_backend.service.KafkaProducerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient saved = patientRepository.save(mapToEntity(patientDTO));
        PatientDTO savedDto = mapToDTO(saved);

        // 🔔 Trigger Kafka log after patient creation
        String logMessage = String.format(
                "New Patient Registered: Name=%s, DOB=%s, Gender=%s, Contact=%s, BloodGroup=%s",
                savedDto.getName(),
                savedDto.getDob(),
                savedDto.getGender(),
                savedDto.getContact(),
                savedDto.getBloodGroup()
        );
        kafkaProducerService.sendPatientLog(logMessage);

        return savedDto;
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        Optional<Patient> optional = patientRepository.findById(id);
        return optional.map(this::mapToDTO).orElse(null);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO updatePatient(Long id, PatientDTO updatedDTO) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient existing = optionalPatient.get();
            existing.setName(updatedDTO.getName());
            existing.setDob(updatedDTO.getDob());
            existing.setGender(updatedDTO.getGender());
            existing.setContact(updatedDTO.getContact());
            existing.setBloodGroup(updatedDTO.getBloodGroup());
            Patient updated = patientRepository.save(existing);
            return mapToDTO(updated);
        }
        return null;
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    private PatientDTO mapToDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setDob(patient.getDob());
        dto.setGender(patient.getGender());
        dto.setContact(patient.getContact());
        dto.setBloodGroup(patient.getBloodGroup());
        return dto;
    }

    private Patient mapToEntity(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setName(dto.getName());
        patient.setDob(dto.getDob());
        patient.setGender(dto.getGender());
        patient.setContact(dto.getContact());
        patient.setBloodGroup(dto.getBloodGroup());
        return patient;
    }
}
