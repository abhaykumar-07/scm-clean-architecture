package com.scm.backend.service.impl;

import com.scm.backend.entity.Contact;
import com.scm.backend.exception.ResourceNotFoundException;
import com.scm.backend.repository.ContactRepository;
import com.scm.backend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact createContact(Contact contact) {
        if (contact.getUser() == null || contact.getUser().getId() == null) {
            throw new IllegalArgumentException("Contact must be linked to a valid user.");
        }
        contact.setId(UUID.randomUUID().toString());
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .filter(c -> c.getUser() != null && c.getUser().getId() != null)
                .toList();
    }

    @Override
    public Contact getContactById(String id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
    }

    @Override
    public void deleteContactById(String id) {
        Contact contact = getContactById(id);
        contactRepository.delete(contact);
    }

    @Override
    public List<Contact> getContactsByUserId(String userId) {
        return contactRepository.findAll()
                .stream()
                .filter(c -> c.getUser() != null && userId.equals(c.getUser().getId()))
                .toList();
    }

    @Override
    public Contact updateContact(String id, Contact contactDetails) {
        Contact contact = getContactById(id);

        contact.setName(contactDetails.getName());
        contact.setEmail(contactDetails.getEmail());
        contact.setPhoneNumber(contactDetails.getPhoneNumber());
        contact.setAddress(contactDetails.getAddress());
        contact.setPicture(contactDetails.getPicture());
        contact.setDescription(contactDetails.getDescription());
        contact.setFavorite(contactDetails.isFavorite());
        contact.setWebsiteLink(contactDetails.getWebsiteLink());
        contact.setLinkedInLink(contactDetails.getLinkedInLink());
        contact.setInstagramLink(contactDetails.getInstagramLink());
        contact.setCloudinaryImagePublicId(contactDetails.getCloudinaryImagePublicId());

        return contactRepository.save(contact);
    }
}
