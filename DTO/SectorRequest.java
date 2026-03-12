package com.smartsalon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SectorRequest {
    
    @NotBlank(message = "Sector name is required")
    private String name;
    
    @NotNull(message = "District ID is required")
    private Long districtId;
    
    // Constructors
    public SectorRequest() {}
    
    public SectorRequest(String name, Long districtId) {
        this.name = name;
        this.districtId = districtId;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Long getDistrictId() { return districtId; }
    public void setDistrictId(Long districtId) { this.districtId = districtId; }
}