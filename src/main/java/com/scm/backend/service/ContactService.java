package com.scm.backend.service;

import com.scm.backend.entity.Contact;
import java.util.List;

public interface ContactService {
    Contact createContact(Contact contact);
    List<Contact> getAllContacts();
    Contact getContactById(String id);

    void deleteContactById(String id);

    List<Contact> getContactsByUserId(String userId);

    Contact updateContact(String id, Contact contactDetails);
}
