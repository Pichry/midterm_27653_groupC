package com.smartsalon.controller;

import com.smartsalon.dto.*;
import com.smartsalon.model.*;
import com.smartsalon.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * LocationController – REST endpoints for complete administrative hierarchy.
 * Province -> District -> Sector -> Cell -> Village
 *
 * Base path: /api/locations
 */
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // ─── Province endpoints ──────────────────────────────────────────────────

    @PostMapping("/provinces")
    public ResponseEntity<?> saveProvince(@Valid @RequestBody ProvinceRequest request) {
        try {
            Province saved = locationService.saveProvince(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/provinces")
    public ResponseEntity<List<Province>> getAllProvinces() {
        return ResponseEntity.ok(locationService.getAllProvinces());
    }

    @GetMapping("/provinces/paginated")
    public ResponseEntity<Page<Province>> getProvincesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(locationService.getAllProvincesPaginated(page, size, sortBy, direction));
    }

    @GetMapping("/provinces/{id}")
    public ResponseEntity<Province> getProvinceById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getProvinceById(id));
    }

    @PutMapping("/provinces/{id}")
    public ResponseEntity<Province> updateProvince(@PathVariable Long id, @Valid @RequestBody ProvinceRequest request) {
        return ResponseEntity.ok(locationService.updateProvince(id, request));
    }

    @DeleteMapping("/provinces/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        locationService.deleteProvince(id);
        return ResponseEntity.noContent().build();
    }

    // ─── District endpoints ──────────────────────────────────────────────────

    @PostMapping("/districts")
    public ResponseEntity<?> saveDistrict(@Valid @RequestBody DistrictRequest request) {
        try {
            District saved = locationService.saveDistrict(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/districts")
    public ResponseEntity<List<District>> getDistrictsByProvince(@RequestParam Long provinceId) {
        return ResponseEntity.ok(locationService.getDistrictsByProvince(provinceId));
    }

    @GetMapping("/districts/paginated")
    public ResponseEntity<Page<District>> getDistrictsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(locationService.getAllDistrictsPaginated(page, size, sortBy, direction));
    }

    @GetMapping("/districts/{id}")
    public ResponseEntity<District> getDistrictById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getDistrictById(id));
    }

    @PutMapping("/districts/{id}")
    public ResponseEntity<District> updateDistrict(@PathVariable Long id, @Valid @RequestBody DistrictRequest request) {
        return ResponseEntity.ok(locationService.updateDistrict(id, request));
    }

    @DeleteMapping("/districts/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        locationService.deleteDistrict(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Sector endpoints ────────────────────────────────────────────────────

    @PostMapping("/sectors")
    public ResponseEntity<?> saveSector(@Valid @RequestBody SectorRequest request) {
        try {
            Sector saved = locationService.saveSector(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/sectors")
    public ResponseEntity<List<Sector>> getSectorsByDistrict(@RequestParam Long districtId) {
        return ResponseEntity.ok(locationService.getSectorsByDistrict(districtId));
    }

    @GetMapping("/sectors/{id}")
    public ResponseEntity<Sector> getSectorById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getSectorById(id));
    }

    @PutMapping("/sectors/{id}")
    public ResponseEntity<Sector> updateSector(@PathVariable Long id, @Valid @RequestBody SectorRequest request) {
        return ResponseEntity.ok(locationService.updateSector(id, request));
    }
    
    @DeleteMapping("/sectors/{id}")
    public ResponseEntity<Void> deleteSector(@PathVariable Long id) {
        locationService.deleteSector(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Cell endpoints ──────────────────────────────────────────────────────

    @PostMapping("/cells")
    public ResponseEntity<?> saveCell(@Valid @RequestBody CellRequest request) {
        try {
            Cell saved = locationService.saveCell(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/cells")
    public ResponseEntity<List<Cell>> getCellsBySector(@RequestParam Long sectorId) {
        return ResponseEntity.ok(locationService.getCellsBySector(sectorId));
    }

    @GetMapping("/cells/{id}")
    public ResponseEntity<Cell> getCellById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getCellById(id));
    }

    @PutMapping("/cells/{id}")
    public ResponseEntity<Cell> updateCell(@PathVariable Long id, @Valid @RequestBody CellRequest request) {
        return ResponseEntity.ok(locationService.updateCell(id, request));
    }

    @DeleteMapping("/cells/{id}")
    public ResponseEntity<Void> deleteCell(@PathVariable Long id) {
        locationService.deleteCell(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Village endpoints (MOST IMPORTANT!) ────────────────────────────────

    @PostMapping("/villages")
    public ResponseEntity<?> saveVillage(@Valid @RequestBody VillageRequest request) {
        try {
            Village saved = locationService.saveVillage(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/villages")
    public ResponseEntity<List<Village>> getVillagesByCell(@RequestParam Long cellId) {
        return ResponseEntity.ok(locationService.getVillagesByCell(cellId));
    }

    @GetMapping("/villages/{id}")
    public ResponseEntity<Village> getVillageById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getVillageById(id));
    }

    @PutMapping("/villages/{id}")
    public ResponseEntity<Village> updateVillage(@PathVariable Long id, @Valid @RequestBody VillageRequest request) {
        return ResponseEntity.ok(locationService.updateVillage(id, request));
    }

    @DeleteMapping("/villages/{id}")
    public ResponseEntity<Void> deleteVillage(@PathVariable Long id) {
        locationService.deleteVillage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/villages/search")
    public ResponseEntity<Village> getVillageByCodeOrName(@RequestParam String identifier) {
        return ResponseEntity.ok(locationService.getVillageByCodeOrName(identifier));
    }
}
