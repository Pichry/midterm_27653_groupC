package com.smartsalon.repository;

import com.smartsalon.model.User;
import com.smartsalon.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository – data access layer for the users table.
 * Updated to support Village-based location hierarchy.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ─── Basic finders ─────────────────────────────────────────────────────

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    List<User> findByRole(UserRole role);

    // ─── existsBy() – Requirement 7 ────────────────────────────────────────

    boolean existsByEmail(String email);
    boolean existsByVillageId(Long villageId);
    
    @Deprecated
    boolean existsByProvinceId(Long provinceId);

	    // ─── Pagination + Sorting – Requirement 3 ──────────────────────────────

	    Page<User> findByRole(UserRole role, Pageable pageable);

	    // ─── Village-based queries (CORRECT IMPLEMENTATION) ────────────────────
	    
	    /**
	     * Find users by village code or name - THIS IS THE CORRECT WAY!
     */
    @Query("SELECT u FROM User u JOIN u.village v WHERE UPPER(v.name) = UPPER(:identifier)")
    List<User> findAllByVillageCodeOrName(@Param("identifier") String identifier);
    
    /**
     * Find users by province through the complete hierarchy: User -> Village -> Cell -> Sector -> District -> Province
     */
    @Query("SELECT u FROM User u JOIN u.village v JOIN v.cell c JOIN c.sector s JOIN s.district d JOIN d.province p WHERE UPPER(p.code) = UPPER(:identifier) OR UPPER(p.name) = UPPER(:identifier)")
    List<User> findAllByProvinceCodeOrName(@Param("identifier") String identifier);
    
    /**
     * Find users by district through hierarchy
     */
    @Query("SELECT u FROM User u JOIN u.village v JOIN v.cell c JOIN c.sector s JOIN s.district d WHERE UPPER(d.name) = UPPER(:identifier)")
    List<User> findAllByDistrictName(@Param("identifier") String identifier);
    
    /**
     * Find users by sector through hierarchy
     */
    @Query("SELECT u FROM User u JOIN u.village v JOIN v.cell c JOIN c.sector s WHERE UPPER(s.name) = UPPER(:identifier)")
    List<User> findAllBySectorCodeOrName(@Param("identifier") String identifier);
    
    /**
     * Find users by cell through hierarchy
     */
    @Query("SELECT u FROM User u JOIN u.village v JOIN v.cell c WHERE UPPER(c.name) = UPPER(:identifier)")
    List<User> findAllByCellCodeOrName(@Param("identifier") String identifier);

    // ─── Legacy province queries (DEPRECATED) ──────────────────────────────
    
    @Deprecated
    @Query("SELECT u FROM User u JOIN u.province p WHERE p.code = :provinceCode")
    List<User> findAllByProvinceCode(@Param("provinceCode") String provinceCode);

    @Deprecated
    @Query("SELECT u FROM User u JOIN u.province p WHERE p.name = :provinceName")
    List<User> findAllByProvinceName(@Param("provinceName") String provinceName);
}
