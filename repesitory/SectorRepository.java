package com.smartsalon.repository;

import com.smartsalon.model.Sector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    
    /**
     * Find all sectors in a specific district
     */
    List<Sector> findByDistrictId(Long districtId);
    
    /**
     * Find sectors by district ID with pagination
     */
    Page<Sector> findByDistrictId(Long districtId, Pageable pageable);
    
    /**
     * Find sectors by name (case-insensitive)
     */
    List<Sector> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find sectors by district name
     */
    @Query("SELECT s FROM Sector s JOIN s.district d WHERE UPPER(d.name) = UPPER(:districtName)")
    List<Sector> findByDistrictName(@Param("districtName") String districtName);
    
    /**
     * Find sectors by province name (through district relationship)
     */
    @Query("SELECT s FROM Sector s JOIN s.district d JOIN d.province p WHERE UPPER(p.name) = UPPER(:provinceName)")
    List<Sector> findByProvinceName(@Param("provinceName") String provinceName);
    
    /**
     * Count sectors in a district
     */
    long countByDistrictId(Long districtId);
    
    /**
     * Find sector by name and district ID
     */
    Optional<Sector> findByNameAndDistrictId(String name, Long districtId);
}