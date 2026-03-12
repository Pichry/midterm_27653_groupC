package com.smartsalon.dto;

import com.smartsalon.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequest {
    @NotBlank
    private String name;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String password;
    
    private String phone;
    private String location;
    
    @NotNull
    private UserRole role;
    
    /**
     * CORRECT: Users should be linked to Village, not Province!
     * This can be either villageId OR village name/code.
     */
    private Long villageId;
    private String villageIdentifier; // name or code
    
    @Deprecated
    private Long provinceId;

    // Constructors
    public UserRequest() {}

    public UserRequest(String name, String email, String password, String phone, String location, UserRole role, Long villageId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.location = location;
        this.role = role;
        this.villageId = villageId;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public Long getVillageId() { return villageId; }
    public void setVillageId(Long villageId) { this.villageId = villageId; }
    
    public String getVillageIdentifier() { return villageIdentifier; }
    public void setVillageIdentifier(String villageIdentifier) { this.villageIdentifier = villageIdentifier; }

    @Deprecated
    public Long getProvinceId() { return provinceId; }
    @Deprecated
    public void setProvinceId(Long provinceId) { this.provinceId = provinceId; }
}
