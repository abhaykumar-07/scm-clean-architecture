package com.scm.backend.controller;

import com.scm.backend.entity.Contact;
import com.scm.backend.model.ContactModel;
import com.scm.backend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        Contact saved = contactService.createContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<ContactModel>> getAllContacts() {
        List<ContactModel> models = contactService.getAllContacts()
                .stream()
                .map(this::mapToModelWithLinks)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(ContactController.class).getAllContacts()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactModel> getContact(@PathVariable String id) {
        Contact contact = contactService.getContactById(id);
        return ResponseEntity.ok(mapToModelWithLinks(contact));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable String id, @RequestBody Contact updatedContact) {
        Contact updated = contactService.updateContact(id, updatedContact);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable String id) {
        contactService.deleteContactById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/contacts")
    public ResponseEntity<CollectionModel<ContactModel>> getContactsByUser(@PathVariable String userId) {
        List<ContactModel> models = contactService.getContactsByUserId(userId)
                .stream()
                .map(this::mapToModelWithLinks)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(ContactController.class).getContactsByUser(userId)).withSelfRel()));
    }

    private ContactModel mapToModelWithLinks(Contact contact) {
        ContactModel model = new ContactModel();
        model.setId(contact.getId());
        model.setName(contact.getName());
        model.setEmail(contact.getEmail());
        model.setPhoneNumber(contact.getPhoneNumber());
        model.setAddress(contact.getAddress());
        model.setPicture(contact.getPicture());
        model.setDescription(contact.getDescription());
        model.setFavorite(contact.isFavorite());
        model.setWebsiteLink(contact.getWebsiteLink());
        model.setLinkedInLink(contact.getLinkedInLink());
        model.setInstagramLink(contact.getInstagramLink());
        model.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

        model.add(linkTo(methodOn(ContactController.class).getContact(contact.getId())).withSelfRel());
        model.add(linkTo(methodOn(ContactController.class).getAllContacts()).withRel("all-contacts"));

        if (contact.getUser() != null && contact.getUser().getId() != null) {
            model.add(linkTo(methodOn(UserController.class).getUser(contact.getUser().getId())).withRel("user"));
        }

        return model;
    }
}
