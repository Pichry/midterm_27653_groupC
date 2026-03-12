package com.smartsalon.repository;

import com.smartsalon.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserProfileRepository – data access for the user_profiles table.
 * Since UserProfile shares the PK with User (@MapsId), the ID type is Long.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    /**
     * Find a profile by the associated user's id.
     * SQL: SELECT * FROM user_profiles WHERE id = ?
     * (id IS the user's id because of @MapsId)
     */
    Optional<UserProfile> findByUserId(Long userId);
}
