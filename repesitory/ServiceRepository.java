package com.smartsalon.repository;

import com.smartsalon.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

	/**
	 * ServiceRepository – data access for the services table.
	 */
	@Repository
	public interface ServiceRepository extends JpaRepository<Service, Long> {

	    List<Service> findByStylistId(Long stylistId);

	    boolean existsByServiceNameAndStylistId(String serviceName, Long stylistId);
	}
