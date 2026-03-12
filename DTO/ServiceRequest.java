package com.smartsalon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ServiceRequest {
    @NotBlank
    private String serviceName;
    
    private String description;
    
    @NotNull
    private BigDecimal price;
    
    private Integer durationMinutes;
    private String image;
    
    @NotNull
    private Long stylistId;

    // Constructors
    public ServiceRequest() {}

    public ServiceRequest(String serviceName, String description, BigDecimal price, Integer durationMinutes, String image, Long stylistId) {
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.durationMinutes = durationMinutes;
        this.image = image;
        this.stylistId = stylistId;
    }

    // Getters and Setters
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Long getStylistId() { return stylistId; }
    public void setStylistId(Long stylistId) { this.stylistId = stylistId; }
}
