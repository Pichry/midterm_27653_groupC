package com.smartsalon.repository;

import com.smartsalon.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ProvinceRepository – data access for the provinces table.
 *
 * Spring Data JPA auto-generates the SQL at runtime by parsing the method names.
 *
 * existsByCode(code)  → SELECT COUNT(*) > 0 FROM provinces WHERE code = ?
 * existsByName(name)  → SELECT COUNT(*) > 0 FROM provinces WHERE name = ?
 * findByCode(code)    → SELECT * FROM provinces WHERE code = ?
 * findByName(name)    → SELECT * FROM provinces WHERE name = ?
 */
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

    Optional<Province> findByCode(String code);

    Optional<Province> findByName(String name);

    /**
     * existsBy*() methods — Requirement 7.
     * Returns true if a province with that code already exists.
     * Internally Spring JPA generates: SELECT count(*) > 0 FROM provinces WHERE code = ?
     */
    boolean existsByCode(String code);

    /**
     * Returns true if a province with that name already exists.
     */
    boolean existsByName(String name);
}
