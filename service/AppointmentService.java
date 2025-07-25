package com.ehrapp.ehr_backend.service;

import com.ehrapp.ehr_backend.entity.Appointment;
import java.util.List;

public interface AppointmentService  {
    Appointment createAppointment(Appointment appointment);
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(Long id);
    Appointment updateAppointment(Long id, Appointment appointment);
    void deleteAppointment(Long id);

    // 👇 Add this line
    List<Appointment> getAppointmentsByDoctorId(Long doctorId);
}
