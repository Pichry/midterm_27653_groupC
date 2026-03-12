package com.smartsalon.repository;

import com.smartsalon.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AvailabilityRepository – data access for the availability table.
 */
@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByStylistId(Long stylistId);

    boolean existsByStylistIdAndDayOfWeek(Long stylistId, String dayOfWeek);
}
