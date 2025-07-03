package com.scm.backend.service.impl;

import com.scm.backend.entity.Providers;
import com.scm.backend.entity.Role;
import com.scm.backend.entity.User;
import com.scm.backend.payload.RegisterRequest;
import com.scm.backend.payload.UserDto;
import com.scm.backend.repository.RoleRepository;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User createUser(User user) {
        System.out.println("RAW PASSWORD = " + user.getPassword());

        System.out.println("Received user: " + user);

        user.setId(UUID.randomUUID().toString());

        // Validate raw password
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role only if none is provided
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            user.setRoles(Set.of(defaultRole));
        }


        return userRepository.save(user);
    }



    @Override
    public User updateUser(String id, User updatedUser) {
        // Fetch the existing user
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Update only the modifiable fields (do NOT update ID)
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            // Only encode new raw password
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        existingUser.setAbout(updatedUser.getAbout());
        existingUser.setProfilePicture(updatedUser.getProfilePicture());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setEnabled(updatedUser.isEnabled());
        existingUser.setEmailVerified(updatedUser.isEmailVerified());
        existingUser.setPhoneVerified(updatedUser.isPhoneVerified());
        existingUser.setProvider(updatedUser.getProvider());
        existingUser.setEmailToken(updatedUser.getEmailToken());

        return userRepository.save(existingUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto registerNewUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAbout(request.getAbout());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEnabled(true);
        user.setEmailVerified(false);
        user.setPhoneVerified(false);
        user.setProvider(Providers.valueOf("SELF"));
        user.setCreatedAt(LocalDateTime.now());
        user.setEmailToken(UUID.randomUUID().toString());

        User saved = userRepository.save(user);

        return new UserDto(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getAbout(),
                saved.getProfilePicture()
        );
    }
}

