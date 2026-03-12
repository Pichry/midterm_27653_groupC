package com.smartsalon.repository;

import com.smartsalon.model.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DistrictRepository – data access for the districts table.
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    /**
     * Find all districts inside a specific province (by province PK).
     * SQL generated: SELECT * FROM districts WHERE province_id = ?
     */
    List<District> findByProvinceId(Long provinceId);

    /**
     * Find districts by province ID with pagination
     */
    Page<District> findByProvinceId(Long provinceId, Pageable pageable);

    boolean existsByNameAndProvinceId(String name, Long provinceId);
}
