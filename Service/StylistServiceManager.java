package com.smartsalon.service;

import com.smartsalon.dto.ServiceRequest;
	import com.smartsalon.model.Service;
	import com.smartsalon.model.User;
	import com.smartsalon.model.UserRole;
	import com.smartsalon.repository.ServiceRepository;
	import com.smartsalon.repository.UserRepository;
	import org.springframework.data.domain.*;
	import org.springframework.lang.NonNull;
	import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * StylistServiceManager – handles the Many-to-Many relationship between
 * User (with role STYLIST) and Service.
 *
 * MANY-TO-MANY logic:
 *   The join table 'stylist_services' has two FK columns:
 *     stylist_id → users.id
 *     service_id → services.id
 *
 *   Adding a service to a stylist:
 *     1. Load the stylist User entity.
 *     2. Load the Service entity.
 *     3. Call stylist.getOfferedServices().add(service).
 *     4. Save the stylist — Hibernate inserts a row into stylist_services.
 *
 *   Removing:
 *     Call stylist.getOfferedServices().remove(service) and save.
 */
@org.springframework.stereotype.Service
public class StylistServiceManager {

    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    public StylistServiceManager(UserRepository userRepository, ServiceRepository serviceRepository) {
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    /**
     * Assigns an existing Service to a Stylist.
     *
     * Relationship handling:
     *   We load both entities, call set.add() on the owning side (User),
     *   then save the User.  Hibernate generates:
     *     INSERT INTO stylist_services (stylist_id, service_id) VALUES (?, ?)
     */
	    @Transactional
	    public User assignServiceToStylist(@NonNull Long stylistId, @NonNull Long serviceId) {
	        User stylist = userRepository.findById(stylistId)
	                .orElseThrow(() -> new IllegalArgumentException("Stylist not found: " + stylistId));

        if (stylist.getRole() != UserRole.STYLIST) {
            throw new IllegalArgumentException("User " + stylistId + " is not a STYLIST.");
        }

        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + serviceId));

        stylist.getOfferedServices().add(service);   // adds to the join table
        return userRepository.save(stylist);
    }

    /**
     * Removes a Service from a Stylist's offered services.
     * Hibernate generates: DELETE FROM stylist_services WHERE stylist_id=? AND service_id=?
     */
	    @Transactional
	    public User removeServiceFromStylist(@NonNull Long stylistId, @NonNull Long serviceId) {
	        User stylist = userRepository.findById(stylistId)
	                .orElseThrow(() -> new IllegalArgumentException("Stylist not found: " + stylistId));

        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + serviceId));

        stylist.getOfferedServices().remove(service);   // removes from join table
        return userRepository.save(stylist);
    }

    /**
     * Returns all services offered by a specific stylist.
     */
	    public Set<Service> getServicesForStylist(@NonNull Long stylistId) {
	        User stylist = userRepository.findById(stylistId)
	                .orElseThrow(() -> new IllegalArgumentException("Stylist not found: " + stylistId));
	        return stylist.getOfferedServices();
	    }

    /**
     * Returns a paginated, sorted list of all services.
     * Demonstrates Requirement 3 (Pagination + Sorting) via ServiceRepository.
     */
    public Page<Service> getAllServicesPaginated(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return serviceRepository.findAll(pageable);
    }

    @Transactional
    public Service createService(ServiceRequest request) {
        Service service = new Service(request.getServiceName(), request.getDescription(), request.getPrice(), request.getDurationMinutes(), request.getImage(), request.getStylistId());
        return serviceRepository.save(service);
    }

	    public Service getServiceById(@NonNull Long id) {
	        return serviceRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + id));
	    }

	    @Transactional
	    public Service updateService(@NonNull Long id, ServiceRequest request) {
	        Service service = getServiceById(id);
        service.setServiceName(request.getServiceName());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setDurationMinutes(request.getDurationMinutes());
        service.setImage(request.getImage());
        service.setStylistId(request.getStylistId());
        return serviceRepository.save(service);
    }

	    @Transactional
	    public void deleteService(@NonNull Long id) {
	        serviceRepository.deleteById(id);
	    }
}
