package com.ehrapp.ehr_backend.repository;

import com.ehrapp.ehr_backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DoctorRepository extends JpaRepository<Doctor,Long>{
}
