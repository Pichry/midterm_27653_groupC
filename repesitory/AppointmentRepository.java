package com.smartsalon.repository;

import com.smartsalon.model.Appointment;
import com.smartsalon.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStylistId(Long stylistId);

    List<Appointment> findByClientId(Long clientId);

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByStylistIdAndAppointmentDate(Long stylistId, LocalDate date);

    List<Appointment> findByClientIdAndStatus(Long clientId, AppointmentStatus status);
}
