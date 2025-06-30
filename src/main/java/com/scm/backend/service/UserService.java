package com.scm.backend.service;

import com.scm.backend.entity.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(String id, User user);  // Added update method
    List<User> getAllUsers();
    User getUserById(String id);
    void deleteUserById(String id);
}
