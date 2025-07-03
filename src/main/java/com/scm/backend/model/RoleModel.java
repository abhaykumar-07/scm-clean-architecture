package com.scm.backend.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel extends RepresentationModel<RoleModel> {
    private Long id;
    private String name;
    private String description;
}
