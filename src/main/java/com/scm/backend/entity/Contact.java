package com.scm.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_contacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;

    @Lob
    private String description;
    private boolean favorite = false;

    private String websiteLink;
    private String linkedInLink;
    private String instagramLink;

    // private List<String> socialLinks=new ArrayList<>();
    private String cloudinaryImagePublicId;

    @ManyToOne
    private User user;
}
