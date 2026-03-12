package com.smartsalon.controller;

import com.smartsalon.dto.UserRequest;
import com.smartsalon.model.User;
import com.smartsalon.model.UserRole;
import com.smartsalon.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController – REST endpoints for User operations.
 *
 * Base path: /api/users
 *
 * Covers:
 *   • Requirement 3: Pagination & Sorting
 *   • Requirement 7: existsBy (email check)
 *   • Requirement 8: retrieve users by province code OR name
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ─── Pagination + Sorting – Requirement 3 ────────────────────────────────

    /**
     * GET /api/users/paginated?page=0&size=10&sortBy=name&direction=asc
     *
     * Returns a paginated, sorted list of all users.
     *
     * Query parameters:
     *   page      - page number (0-indexed, default 0)
     *   size      - records per page (default 10)
     *   sortBy    - field to sort by (default "name")
     *   direction - "asc" or "desc" (default "asc")
     *
     * The Page<User> response body includes:
     *   content          - the list of users for this page
     *   totalElements    - total user count in the database
     *   totalPages       - total number of pages
     *   number           - current page index
     *   size             - page size used
     *   first / last     - whether this is the first/last page
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<User>> getUsersPaginated(
            @RequestParam(defaultValue = "0")    int page,
            @RequestParam(defaultValue = "10")   int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc")  String direction) {

        return ResponseEntity.ok(userService.getAllUsersPaginated(page, size, sortBy, direction));
    }

    /**
     * GET /api/users/by-role?role=CLIENT&page=0&size=10&sortBy=createdAt&direction=desc
     *
     * Returns a paginated, sorted page of users filtered by role.
     */
    @GetMapping("/by-role")
    public ResponseEntity<Page<User>> getUsersByRole(
            @RequestParam UserRole role,
            @RequestParam(defaultValue = "0")          int page,
            @RequestParam(defaultValue = "10")         int size,
            @RequestParam(defaultValue = "createdAt")  String sortBy,
            @RequestParam(defaultValue = "desc")       String direction) {

        return ResponseEntity.ok(userService.getUsersByRolePaginated(role, page, size, sortBy, direction));
    }

    // ─── existsBy – Requirement 7 ─────────────────────────────────────────────

    /**
     * GET /api/users/exists?email=test@example.com
     *
     * Returns true/false — whether an account with that email exists.
     * Uses existsByEmail() from UserRepository, which generates a COUNT query —
     * no entity object is loaded, making it very efficient.
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        return ResponseEntity.ok(userService.emailAlreadyRegistered(email));
    }

    // ─── Province queries – Requirement 8 ────────────────────────────────────

    /**
     * GET /api/users/by-province?identifier=KIG
     *   OR
     * GET /api/users/by-province?identifier=Kigali+City
     *
     * Accepts EITHER a province code OR a province name.
     * The service layer calls the combined JPQL query that checks BOTH.
     *
     * Logic:
     *   UserRepository.findAllByProvinceCodeOrName() runs:
     *   SELECT u FROM User u JOIN u.province p
     *   WHERE UPPER(p.code) = UPPER(:identifier) OR UPPER(p.name) = UPPER(:identifier)
     */
    @GetMapping("/by-province")
    public ResponseEntity<List<User>> getUsersByProvince(@RequestParam String identifier) {
        return ResponseEntity.ok(userService.getUsersByProvinceCodeOrName(identifier));
    }

    /**
     * GET /api/users/by-province/code?code=KIG
     * Explicit code-only variant.
     */
    @GetMapping("/by-province/code")
    public ResponseEntity<List<User>> getUsersByProvinceCode(@RequestParam String code) {
        return ResponseEntity.ok(userService.getUsersByProvinceCode(code));
    }

    /**
     * GET /api/users/by-province/name?name=Kigali+City
     * Explicit name-only variant.
     */
    @GetMapping("/by-province/name")
    public ResponseEntity<List<User>> getUsersByProvinceName(@RequestParam String name) {
        return ResponseEntity.ok(userService.getUsersByProvinceName(name));
    }

    // ─── Basic ────────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
