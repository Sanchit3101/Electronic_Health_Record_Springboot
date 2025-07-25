package com.ehrapp.ehr_backend.repository;

import com.ehrapp.ehr_backend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
