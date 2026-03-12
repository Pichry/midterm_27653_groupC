package com.smartsalon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CellRequest {
    
    @NotBlank(message = "Cell name is required")
    private String name;
    
    @NotNull(message = "Sector ID is required")
    private Long sectorId;
    
    // Constructors
    public CellRequest() {}
    
    public CellRequest(String name, Long sectorId) {
        this.name = name;
        this.sectorId = sectorId;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Long getSectorId() { return sectorId; }
    public void setSectorId(Long sectorId) { this.sectorId = sectorId; }
}