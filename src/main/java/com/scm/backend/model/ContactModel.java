package com.scm.backend.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactModel extends RepresentationModel<ContactModel> {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;
    private String instagramLink;
    private String cloudinaryImagePublicId;
}