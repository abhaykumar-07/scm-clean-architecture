package com.scm.backend.service.impl;

import com.scm.backend.entity.User;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
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
        existingUser.setPassword(updatedUser.getPassword());
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
}
