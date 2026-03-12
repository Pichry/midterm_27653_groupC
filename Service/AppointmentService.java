package com.smartsalon.service;

	import com.smartsalon.model.Appointment;
	import com.smartsalon.model.AppointmentStatus;
	import com.smartsalon.repository.AppointmentRepository;
	import org.springframework.lang.NonNull;
	import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getByStylist(Long stylistId) {
        return appointmentRepository.findByStylistId(stylistId);
    }

    public List<Appointment> getByClient(Long clientId) {
        return appointmentRepository.findByClientId(clientId);
    }

    public List<Appointment> getByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    public List<Appointment> getByStylistAndDate(Long stylistId, LocalDate date) {
        return appointmentRepository.findByStylistIdAndAppointmentDate(stylistId, date);
    }

    public List<Appointment> getByClientAndStatus(Long clientId, AppointmentStatus status) {
        return appointmentRepository.findByClientIdAndStatus(clientId, status);
    }

	    public @NonNull Appointment save(@NonNull Appointment appointment) {
	        return appointmentRepository.save(appointment);
	    }

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }
}
