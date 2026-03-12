package com.smartsalon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for saving a Province.
 * Validation annotations ensure the client sends valid data.
 */
public class ProvinceRequest {

    @NotBlank(message = "Province code is required")
    @Size(max = 10, message = "Code must be at most 10 characters")
    private String code;

    @NotBlank(message = "Province name is required")
    private String name;

    // Constructors
    public ProvinceRequest() {}

    public ProvinceRequest(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
