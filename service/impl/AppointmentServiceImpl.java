package com.ehrapp.ehr_backend.service.impl;

import com.ehrapp.ehr_backend.entity.Appointment;
import com.ehrapp.ehr_backend.service.AppointmentService;
import com.ehrapp.ehr_backend.repository.AppointmentRepository;
import com.ehrapp.ehr_backend.entity.Doctor;
import com.ehrapp.ehr_backend.entity.Patient;
import com.ehrapp.ehr_backend.repository.DoctorRepository;
import com.ehrapp.ehr_backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;



    @Override
    public Appointment createAppointment(Appointment appointment) {
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).orElse(null);
        Patient patient = patientRepository.findById(appointment.getPatient().getId()).orElse(null);

        if (doctor != null && patient != null) {
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            return appointmentRepository.save(appointment);
        }

        return null; // doctor or patient not found
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public Appointment updateAppointment(Long id, Appointment appointment) {
        Appointment existing = appointmentRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setDateTime(appointment.getDateTime());
            existing.setStatus(appointment.getStatus());

            if (appointment.getDoctor() != null) {
                Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).orElse(null);
                existing.setDoctor(doctor);
            }

            if (appointment.getPatient() != null) {
                Patient patient = patientRepository.findById(appointment.getPatient().getId()).orElse(null);
                existing.setPatient(patient);
            }

            return appointmentRepository.save(existing);
        }

        return null;
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
}
