package com.scm.backend.service;

import com.scm.backend.entity.User;
import com.scm.backend.payload.RegisterRequest;
import com.scm.backend.payload.UserDto;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(String id, User user);  // Added update method
    List<User> getAllUsers();
    User getUserById(String id);
    void deleteUserById(String id);
    UserDto registerNewUser(RegisterRequest request);

}
