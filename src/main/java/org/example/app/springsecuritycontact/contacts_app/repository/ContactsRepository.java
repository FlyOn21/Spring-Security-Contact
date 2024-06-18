package org.example.app.springsecuritycontact.contacts_app.repository;

import org.example.app.springsecuritycontact.auth_app.entitys.entity.User;
import org.example.app.springsecuritycontact.contacts_app.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactsRepository extends JpaRepository<Contact, Long> {
    Iterable<Contact> findByOwner(User user);
}
