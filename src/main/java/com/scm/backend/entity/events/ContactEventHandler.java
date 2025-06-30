//package com.scm.backend.entity.events;
//
//import com.scm.backend.entity.Contact;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
//import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Component
//@RepositoryEventHandler(Contact.class)
//public class ContactEventHandler {
//
//    private Logger log = (Logger) LoggerFactory.getLogger(ContactEventHandler.class);
//
//
//    @HandleBeforeCreate
//    public void beforeSave(Contact contact){
//
//        log.info("going to create Contact");
//        contact.setId(UUID.randomUUID().toString());
//    }
//}
