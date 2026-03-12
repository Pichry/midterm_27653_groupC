package com.smartsalon.service;

import com.smartsalon.dto.UserRequest;
import com.smartsalon.model.Village;
	import com.smartsalon.model.User;
	import com.smartsalon.model.UserRole;
	import com.smartsalon.repository.VillageRepository;
	import com.smartsalon.repository.UserRepository;
	import org.springframework.data.domain.*;
	import org.springframework.lang.NonNull;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.stereotype.Service;
	import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final VillageRepository villageRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, VillageRepository villageRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.villageRepository = villageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailAlreadyRegistered(String email) {
        return userRepository.existsByEmail(email);
    }

    public Page<User> getAllUsersPaginated(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }

    public Page<User> getUsersByRolePaginated(UserRole role, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findByRole(role, pageable);
    }

    public List<User> getUsersByProvinceCode(String provinceCode) {
        return userRepository.findAllByProvinceCodeOrName(provinceCode.toUpperCase());
    }

    public List<User> getUsersByProvinceName(String provinceName) {
        return userRepository.findAllByProvinceCodeOrName(provinceName);
    }

    public List<User> getUsersByProvinceCodeOrName(String identifier) {
        return userRepository.findAllByProvinceCodeOrName(identifier);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

	    public User getUserById(@NonNull Long id) {
	        return userRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
	    }

	    @Transactional
	    public User createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        User user = new User(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getPhone(), request.getLocation(), request.getRole());
        
	        Long villageId = request.getVillageId();
	        if (villageId != null && villageId > 0) {
	            villageRepository.findById(villageId).ifPresent(user::setVillage);
	        }
        
        return userRepository.save(user);
    }

	    @Transactional
	    public User updateUser(@NonNull Long id, UserRequest request) {
	        User user = getUserById(id);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setPhone(request.getPhone());
        user.setLocation(request.getLocation());
        user.setRole(request.getRole());
        
	        Long villageId = request.getVillageId();
	        if (villageId != null && villageId > 0) {
	            villageRepository.findById(villageId).ifPresent(user::setVillage);
	        }
        
        return userRepository.save(user);
    }

	    @Transactional
	    public void deleteUser(@NonNull Long id) {
	        userRepository.deleteById(id);
	    }
}
