package com.scm.backend.controller;

import com.scm.backend.entity.Role;
import com.scm.backend.model.RoleModel;
import com.scm.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<CollectionModel<RoleModel>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        List<RoleModel> roleModels = roles.stream().map(role -> {
            RoleModel model = new RoleModel();
            model.setId(role.getId());
            model.setName(role.getName());
            model.setDescription(role.getDescription());

            model.add(linkTo(methodOn(RoleController.class).getRoleById(role.getId())).withSelfRel());
            model.add(linkTo(methodOn(RoleController.class).getAllRoles()).withRel("role"));
            model.add(linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

            return model;
        }).toList();

        return ResponseEntity.ok(CollectionModel.of(roleModels,
                linkTo(methodOn(RoleController.class).getAllRoles()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleModel> getRoleById(@PathVariable Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        RoleModel model = new RoleModel();
        model.setId(role.getId());
        model.setName(role.getName());
        model.setDescription(role.getDescription());

        model.add(linkTo(methodOn(RoleController.class).getRoleById(id)).withSelfRel());
        model.add(linkTo(methodOn(RoleController.class).getAllRoles()).withRel("role"));
        model.add(linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

        return ResponseEntity.ok(model);
    }
}
