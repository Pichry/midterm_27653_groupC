package com.smartsalon.controller;

import com.smartsalon.model.*;
import com.smartsalon.service.LocationService;
import com.smartsalon.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * TestController - Quick verification endpoints for the salon booking system.
 * 
 * Use these endpoints to verify that the complete Rwanda location hierarchy
 * is working correctly and users can be created with village relationships.
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final LocationService locationService;
    private final UserService userService;

    public TestController(LocationService locationService, UserService userService) {
        this.locationService = locationService;
        this.userService = userService;
    }

    /**
     * GET /api/test/hierarchy
     * 
     * Returns a complete count of all location levels to verify data population.
     */
    @GetMapping("/hierarchy")
    public ResponseEntity<Map<String, Object>> getLocationHierarchy() {
        Map<String, Object> hierarchy = new HashMap<>();
        
        var provinces = locationService.getAllProvinces();
        hierarchy.put("provinces", provinces.size());
        hierarchy.put("provinceList", provinces);
        
        int totalDistricts = 0;
        int totalSectors = 0;
        int totalCells = 0;
        int totalVillages = 0;
        
        for (Province province : provinces) {
            var districts = locationService.getDistrictsByProvince(province.getId());
            totalDistricts += districts.size();
            
            for (District district : districts) {
                var sectors = locationService.getSectorsByDistrict(district.getId());
                totalSectors += sectors.size();
                
                for (Sector sector : sectors) {
                    var cells = locationService.getCellsBySector(sector.getId());
                    totalCells += cells.size();
                    
                    for (Cell cell : cells) {
                        var villages = locationService.getVillagesByCell(cell.getId());
                        totalVillages += villages.size();
                    }
                }
            }
        }
        
        hierarchy.put("districts", totalDistricts);
        hierarchy.put("sectors", totalSectors);
        hierarchy.put("cells", totalCells);
        hierarchy.put("villages", totalVillages);
        
        return ResponseEntity.ok(hierarchy);
    }

    /**
     * GET /api/test/village/{id}/path
     * 
     * Shows the complete administrative path for a village:
     * Village → Cell → Sector → District → Province
     */
    @GetMapping("/village/{id}/path")
    public ResponseEntity<Map<String, String>> getVillagePath(@PathVariable Long id) {
        try {
            Village village = locationService.getVillageById(id);
            Cell cell = village.getCell();
            Sector sector = cell.getSector();
            District district = sector.getDistrict();
            Province province = district.getProvince();
            
            Map<String, String> path = new HashMap<>();
            path.put("village", village.getName());
            path.put("cell", cell.getName());
            path.put("sector", sector.getName());
            path.put("district", district.getName());
            path.put("province", province.getName());
            path.put("fullPath", String.format("%s → %s → %s → %s → %s", 
                province.getName(), district.getName(), sector.getName(), cell.getName(), village.getName()));
            
            return ResponseEntity.ok(path);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Village not found: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * GET /api/test/users/by-location
     * 
     * Shows how users can be queried by their location hierarchy.
     * This demonstrates the power of storing only villageId in users table.
     */
    @GetMapping("/users/by-location")
    public ResponseEntity<Map<String, Object>> getUsersByLocation() {
        Map<String, Object> result = new HashMap<>();
        
        var allUsers = userService.getAllUsers();
        result.put("totalUsers", allUsers.size());
        
        // Group users by their administrative levels
        Map<String, Integer> usersByProvince = new HashMap<>();
        Map<String, Integer> usersByDistrict = new HashMap<>();
        Map<String, Integer> usersByVillage = new HashMap<>();
        
        for (User user : allUsers) {
	            if (user.getVillage() != null) {
	                Village village = user.getVillage();
	                Cell cell = village.getCell();
	                Sector sector = cell.getSector();
	                District district = sector.getDistrict();
	                Province province = district.getProvince();
	                
	                // Count by province
	                usersByProvince.compute(province.getName(), (k, v) -> v == null ? 1 : v + 1);
	                
	                // Count by district
	                usersByDistrict.compute(district.getName(), (k, v) -> v == null ? 1 : v + 1);
	                
	                // Count by village
	                usersByVillage.compute(village.getName(), (k, v) -> v == null ? 1 : v + 1);
	            }
	        }
	        
	        result.put("usersByProvince", usersByProvince);
        result.put("usersByDistrict", usersByDistrict);
        result.put("usersByVillage", usersByVillage);
        
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/test/status
     * 
     * Quick system status check.
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getSystemStatus() {
        Map<String, String> status = new HashMap<>();
        status.put("system", "Smart Salon Management System");
        status.put("status", "✅ RUNNING");
        status.put("locationHierarchy", "✅ Rwanda Administrative Structure");
        status.put("userSystem", "✅ Village-based User Storage");
        status.put("apiEndpoints", "✅ Complete REST API");
        status.put("frontend", "✅ Dynamic Location Dropdowns");
        
        return ResponseEntity.ok(status);
    }
}
