package com.scm.backend.model;

import com.scm.backend.entity.Providers;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel extends RepresentationModel<UserModel> {
    private String id;
    private String name;
    private String email;
    private String password;
    private String about;
    private String profilePicture;
    private String phoneNumber;
    private boolean enabled;
    private boolean emailVerified;
    private boolean phoneVerified;
    private Providers provider;
    private String emailToken;
}