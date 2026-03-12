package com.smartsalon.repository;

import com.smartsalon.model.Cell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {
    
    /**
     * Find all cells in a specific sector
     */
    List<Cell> findBySectorId(Long sectorId);
    
    /**
     * Find cells by sector ID with pagination
     */
    Page<Cell> findBySectorId(Long sectorId, Pageable pageable);
    
    /**
     * Find cells by name (case-insensitive)
     */
    List<Cell> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find cells by sector name
     */
    @Query("SELECT c FROM Cell c JOIN c.sector s WHERE UPPER(s.name) = UPPER(:sectorName)")
    List<Cell> findBySectorName(@Param("sectorName") String sectorName);
    
    /**
     * Find cells by district name (through sector relationship)
     */
    @Query("SELECT c FROM Cell c JOIN c.sector s JOIN s.district d WHERE UPPER(d.name) = UPPER(:districtName)")
    List<Cell> findByDistrictName(@Param("districtName") String districtName);
    
    /**
     * Find cells by province name (through sector and district relationships)
     */
    @Query("SELECT c FROM Cell c JOIN c.sector s JOIN s.district d JOIN d.province p WHERE UPPER(p.name) = UPPER(:provinceName)")
    List<Cell> findByProvinceName(@Param("provinceName") String provinceName);
    
    /**
     * Count cells in a sector
     */
    long countBySectorId(Long sectorId);
    
    /**
     * Find cell by name and sector ID
     */
    Optional<Cell> findByNameAndSectorId(String name, Long sectorId);
}