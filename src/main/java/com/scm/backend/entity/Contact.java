package com.scm.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Contact name is required")
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
    private String phoneNumber;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;

    @Column(length = 1000)
    private String picture;

    @Lob
    @NotBlank(message = "Description required")
    private String description;

    private boolean favorite = false;

    @Size(max = 250, message = "Website link must not exceed 250 characters")
    @Pattern(
            regexp = "^(https?://)([\\w\\-]+\\.)+[\\w\\-]+(:\\d+)?(/\\S*)?$",
            message = "Website link must be a valid URL starting with http:// or https://"
    )
    private String websiteLink;

    @Size(max = 250, message = "LinkedIn link must not exceed 250 characters")
    @Pattern(
            regexp = "^(https?://)([\\w\\-]+\\.)+[\\w\\-]+(:\\d+)?(/\\S*)?$",
            message = "LinkedIn link must be a valid URL starting with http:// or https://"
    )
    private String linkedInLink;

    @Size(max = 250, message = "Instagram link must not exceed 250 characters")
    @Pattern(
            regexp = "^(https?://)([\\w\\-]+\\.)+[\\w\\-]+(:\\d+)?(/\\S*)?$",
            message = "Instagram link must be a valid URL starting with http:// or https://"
    )
    private String instagramLink;

    @Size(max = 250, message = "Cloudinary image public ID must not exceed 250 characters")
    private String cloudinaryImagePublicId;

    @ManyToOne
    @JsonBackReference(value = "user-contact")
    private User user;
}
