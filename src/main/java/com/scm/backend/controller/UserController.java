package com.scm.backend.controller;

import com.scm.backend.entity.User;
import com.scm.backend.model.UserModel;
import com.scm.backend.service.ImageUploadService;
import com.scm.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    ImageUploadService imageUploadService;


    @PostMapping
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody User user) {
        User saved = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToModel(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable String id) {
        User user = userService.getUserById(id);
        UserModel model = convertToModel(user);
        return ResponseEntity.ok(model);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<UserModel>> getUsers() {
        List<UserModel> models = userService.getAllUsers()
                .stream()
                .map(this::convertToModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(UserController.class).getUsers()).withSelfRel()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(convertToModel(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
//       To upload image on cloud
    @PostMapping("/{userId}/profile-picture")
    public ResponseEntity<?> uploadProfilePicture(
            @PathVariable String userId,
            @RequestParam("image") MultipartFile image) {

        User user = userService.getUserById(userId);
        String imageUrl = imageUploadService.uploadFile(image);

        user.setProfilePicture(imageUrl);
        userService.updateUser(userId, user);

        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
    }


    private UserModel convertToModel(User user) {
        UserModel model = new UserModel();
        model.setId(user.getId());
        model.setName(user.getName());
        model.setEmail(user.getEmail());
//        model.setPassword(user.getPassword());
        model.setAbout(user.getAbout());
        model.setProfilePicture(user.getProfilePicture());
        model.setPhoneNumber(user.getPhoneNumber());
        model.setEnabled(user.isEnabled());
        model.setEmailVerified(user.isEmailVerified());
        model.setPhoneVerified(user.isPhoneVerified());
        model.setProvider(user.getProvider());
        model.setEmailToken(user.getEmailToken());

        // Add RESTful HATEOAS links
        model.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).getUsers()).withRel("all-users"));
        model.add(linkTo(methodOn(ContactController.class).getContactsByUser(user.getId())).withRel("contacts"));

        return model;
    }
}
