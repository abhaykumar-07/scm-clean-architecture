//package com.scm.backend.repository;
//
//import com.scm.backend.entity.Contact;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
//import org.springframework.stereotype.Repository;
//
//import java.awt.print.Pageable;
//import java.util.List;
//
//@Repository
//@RepositoryRestResource(path = "contacts", collectionResourceRel = "contacts")
//public interface ContactRepository extends JpaRepository<Contact,String> {
//
////    /contacts/search/email ? email = abc@hmail.com
//    @RestResource(path = "by-email")
//    List<Contact> findByEmailContaining(@Param("email") String email);
//
//    @RestResource(path = "by-phone", rel = "by-phone")
//    List<Contact> findByPhoneNumberContainingIgnoreCase(@Param("phone") String phoneNumber);
//
//    @RestResource(path = "by-name")
//    List<Contact> findByNameContainingIgnoreCase(@Param("name") String name);
//}
package com.scm.backend.repository;

import com.scm.backend.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, String> {
    List<Contact> findByEmailContaining(String email);
}
