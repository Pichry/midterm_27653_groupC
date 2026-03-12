package com.smartsalon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VillageRequest {
    
    @NotBlank(message = "Village name is required")
    private String name;
    
    @NotNull(message = "Cell ID is required")
    private Long cellId;
    
    // Constructors
    public VillageRequest() {}
    
    public VillageRequest(String name, Long cellId) {
        this.name = name;
        this.cellId = cellId;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Long getCellId() { return cellId; }
    public void setCellId(Long cellId) { this.cellId = cellId; }
}