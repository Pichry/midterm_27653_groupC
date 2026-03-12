package com.smartsalon.repository;

import com.smartsalon.model.Village;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {
    
    /**
     * Find all villages in a specific cell
     */
    List<Village> findByCellId(Long cellId);
    
    /**
     * Find villages by cell ID with pagination
     */
    Page<Village> findByCellId(Long cellId, Pageable pageable);
    
    /**
     * Find villages by name (case-insensitive)
     */
    List<Village> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find villages by cell name
     */
    @Query("SELECT v FROM Village v JOIN v.cell c WHERE UPPER(c.name) = UPPER(:cellName)")
    List<Village> findByCellName(@Param("cellName") String cellName);
    
    /**
     * Find villages by sector name (through cell relationship)
     */
    @Query("SELECT v FROM Village v JOIN v.cell c JOIN c.sector s WHERE UPPER(s.name) = UPPER(:sectorName)")
    List<Village> findBySectorName(@Param("sectorName") String sectorName);
    
    /**
     * Find villages by district name (through cell and sector relationships)
     */
    @Query("SELECT v FROM Village v JOIN v.cell c JOIN c.sector s JOIN s.district d WHERE UPPER(d.name) = UPPER(:districtName)")
    List<Village> findByDistrictName(@Param("districtName") String districtName);
    
    /**
     * Find villages by province name (through all relationships)
     */
    @Query("SELECT v FROM Village v JOIN v.cell c JOIN c.sector s JOIN s.district d JOIN d.province p WHERE UPPER(p.name) = UPPER(:provinceName)")
    List<Village> findByProvinceName(@Param("provinceName") String provinceName);
    
    /**
     * Count villages in a cell
     */
    long countByCellId(Long cellId);
    
    /**
     * Get village with full location hierarchy
     */
    @Query("SELECT v FROM Village v " +
           "JOIN FETCH v.cell c " +
           "JOIN FETCH c.sector s " +
           "JOIN FETCH s.district d " +
           "JOIN FETCH d.province p " +
           "WHERE v.id = :villageId")
    Village findByIdWithFullHierarchy(@Param("villageId") Long villageId);
    
    /**
     * Find village by name and cell ID
     */
    Optional<Village> findByNameAndCellId(String name, Long cellId);
    
    /**
     * Find village by code or name
     */
    @Query("SELECT v FROM Village v WHERE v.name = :identifier")
    Optional<Village> findByCodeOrName(@Param("identifier") String identifier);
}