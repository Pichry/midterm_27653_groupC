package com.smartsalon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for saving a District (linked to an existing Province).
 */
public class DistrictRequest {

    @NotBlank(message = "District name is required")
    private String name;

    @NotNull(message = "Province ID is required")
    private Long provinceId;

    // Constructors
    public DistrictRequest() {}

    public DistrictRequest(String name, Long provinceId) {
        this.name = name;
        this.provinceId = provinceId;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getProvinceId() { return provinceId; }
    public void setProvinceId(Long provinceId) { this.provinceId = provinceId; }
}
