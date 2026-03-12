package com.smartsalon.controller;

import com.smartsalon.dto.ServiceRequest;
import com.smartsalon.model.Service;
import com.smartsalon.model.User;
import com.smartsalon.service.StylistServiceManager;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * ServiceController – REST endpoints for Service operations.
 *
 * Base path: /api/services
 *
 * Covers:
 *   • Requirement 3: Pagination + Sorting for services
 *   • Requirement 4: Many-to-Many (assign/remove service to/from stylist)
 */
@RestController
@RequestMapping("/services")
public class ServiceController {

    private final StylistServiceManager stylistServiceManager;

    public ServiceController(StylistServiceManager stylistServiceManager) {
        this.stylistServiceManager = stylistServiceManager;
    }

    // ─── Pagination + Sorting – Requirement 3 ────────────────────────────────

    /**
     * GET /api/services/paginated?page=0&size=5&sortBy=price&direction=asc
     *
     * Returns a sorted, paginated list of all services.
     * sortBy examples: "serviceName", "price", "durationMinutes"
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<Service>> getServicesPaginated(
            @RequestParam(defaultValue = "0")            int page,
            @RequestParam(defaultValue = "5")            int size,
            @RequestParam(defaultValue = "serviceName")  String sortBy,
            @RequestParam(defaultValue = "asc")          String direction) {

        return ResponseEntity.ok(
            stylistServiceManager.getAllServicesPaginated(page, size, sortBy, direction)
        );
    }

    // ─── Many-to-Many – Requirement 4 ────────────────────────────────────────

    /**
     * POST /api/services/assign?stylistId=2&serviceId=5
     *
     * Assigns a Service to a Stylist (inserts row into stylist_services join table).
     *
     * Join table logic:
     *   stylist_services has two columns:
     *     stylist_id (FK → users.id)
     *     service_id (FK → services.id)
     *   Hibernate INSERT: INSERT INTO stylist_services (stylist_id, service_id) VALUES (?, ?)
     */
    @PostMapping("/assign")
    public ResponseEntity<User> assignServiceToStylist(
            @RequestParam Long stylistId,
            @RequestParam Long serviceId) {

        return ResponseEntity.ok(
            stylistServiceManager.assignServiceToStylist(stylistId, serviceId)
        );
    }

    /**
     * DELETE /api/services/assign?stylistId=2&serviceId=5
     *
     * Removes a Service from a Stylist.
     * Hibernate: DELETE FROM stylist_services WHERE stylist_id=? AND service_id=?
     */
    @DeleteMapping("/assign")
    public ResponseEntity<User> removeServiceFromStylist(
            @RequestParam Long stylistId,
            @RequestParam Long serviceId) {

        return ResponseEntity.ok(
            stylistServiceManager.removeServiceFromStylist(stylistId, serviceId)
        );
    }

    /**
     * GET /api/services/stylist/{stylistId}
     * Returns all services offered by a given stylist.
     */
    @GetMapping("/stylist/{stylistId}")
    public ResponseEntity<Set<Service>> getServicesForStylist(@PathVariable Long stylistId) {
        return ResponseEntity.ok(stylistServiceManager.getServicesForStylist(stylistId));
    }

    @PostMapping
    public ResponseEntity<Service> createService(@Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stylistServiceManager.createService(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(stylistServiceManager.getServiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(stylistServiceManager.updateService(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        stylistServiceManager.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
