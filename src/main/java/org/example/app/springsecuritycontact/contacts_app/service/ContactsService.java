package org.example.app.springsecuritycontact.contacts_app.service;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.springsecuritycontact.apps_dto.ResponseDataDTO;
import org.example.app.springsecuritycontact.auth_app.entitys.entity.User;
import org.example.app.springsecuritycontact.auth_app.utils.GetCurrentUser;
import org.example.app.springsecuritycontact.contacts_app.entity.Contact;
import org.example.app.springsecuritycontact.contacts_app.repository.ContactsRepository;
import org.example.app.springsecuritycontact.contacts_app.service.DTO.ContactDTO;
import org.example.app.springsecuritycontact.exceptions.custome_exception.CRUDException;
import org.example.app.springsecuritycontact.exceptions.custome_exception.ValidationException;
import org.example.app.springsecuritycontact.validate.contacts_form.FormDataForValidateDTO;
import org.example.app.springsecuritycontact.validate.contacts_form.ValidationContactsFormProcessing;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("customerService")
public class ContactsService {

    @Autowired
    private ContactsRepository repository;

    private User user;

    private static final Logger SERVICE_LOGGER =
            LogManager.getLogger(ContactsService.class);
    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");


    public ResponseDataDTO<Contact> create(FormDataForValidateDTO input, String fragmentName, HttpSession session) {
        user = session.getAttribute("user") == null ? new GetCurrentUser().getCurrentUser() : (User) session.getAttribute("user");
        try {
            ValidationContactsFormProcessing validationForm = new ValidationContactsFormProcessing(repository, input);
            if (!validationForm.getIsValidForm()) {
                throw new ValidationException("Validation create form failed", validationForm, "contact_add");
            }

            Contact contact = new Contact(
                    input.getFirstName(),
                    input.getLastName(),
                    input.getEmail(),
                    input.getPhoneNumber(),
                    user
                    );
                Contact queryResult = repository.save(contact);
                return new ResponseDataDTO<>(
                        true,
                        true,
                        List.of(queryResult),
                        null,
                        "Contact created successfully",
                        Collections.emptyList(),
                        null
                );
        } catch (Exception e) {
            if (e instanceof DataAccessException || e instanceof HibernateException) {
                SERVICE_LOGGER.error("Failed to create contact: {}", e.getMessage());
                CONSOLE_LOGGER.error("Failed to create contact: {}", e.getMessage());
                throw new CRUDException("Failed to create contact", fragmentName);
            }
            throw e;
        }
    }

        public ResponseDataDTO<ContactDTO> getAll(String fragmentName, HttpSession session) {
            user = session.getAttribute("user") == null ? new GetCurrentUser().getCurrentUser() : (User) session.getAttribute("user");
        try{
            Iterable<Contact> queryResult = repository.findByOwner(user);
            List<ContactDTO> customerDTOList = StreamSupport.stream(queryResult.spliterator(), false)
                    .map(ContactDTO::new)
                    .collect(Collectors.toList());

            return new ResponseDataDTO<>(
                    true,
                    true,
                    customerDTOList,
                    null,
                    "Contacts received successfully",
                    Collections.emptyList(),
                    null
            );
        } catch (Exception e) {
            if (e instanceof DataAccessException || e instanceof HibernateException) {
                SERVICE_LOGGER.error("Failed to get contacts: {}", e.getMessage(), e);
                CONSOLE_LOGGER.error("Failed to get contacts: {}", e.getMessage(), e);
                throw new CRUDException("Failed to get contacts", fragmentName);
            }
            throw e;
        }
    }


    public ResponseDataDTO<Contact> getById(Long id, String fragmentName) {
        try {
            Optional<Contact> getByIdResult = repository.findById(id);
            return getByIdResult.map(contact -> new ResponseDataDTO<>(
                    true,
                    true,
                    List.of(contact),
                    null,
                    "Contact received successfully",
                    Collections.emptyList(),
                    null
            )).orElseGet(() -> new ResponseDataDTO<>(
                    false,
                    false,
                    null,
                    null,
                    "Contact not found",
                    List.of("Contact not found"),
                    null
            ));
        } catch (Exception e) {
            if (e instanceof DataAccessException || e instanceof HibernateException) {
                SERVICE_LOGGER.error("Failed to get contact by id: {}", e.getMessage(), e);
                CONSOLE_LOGGER.error("Failed to get contact by id: {}", e.getMessage(), e);
                throw new CRUDException("Failed to get by id contact", fragmentName);
            }
            throw e;
        }
    }

    public ResponseDataDTO<Contact> update(FormDataForValidateDTO input, String fragmentName) {
        try {
            Optional<Contact> contactData = repository.findById(input.getId());
            if (contactData.isEmpty()) {
                return new ResponseDataDTO<>(
                        false,
                        false,
                        null,
                        null,
                        "Contact not found",
                        List.of("Contact not found"),
                        null
                );
            }
            Contact contact = contactData.get();
            ValidationContactsFormProcessing validationForm = new ValidationContactsFormProcessing(repository, input, contact.getId());
            if (!validationForm.getIsValidForm()) {
                throw new ValidationException("Validation update form failed", validationForm, fragmentName);
            }

            contact.setFirstName(input.getFirstName() == null ? contact.getFirstName() : input.getFirstName());
            contact.setLastName(input.getLastName() == null ? contact.getLastName() : input.getLastName());
            contact.setEmail(input.getEmail() == null ? contact.getEmail() : input.getEmail());
            contact.setPhoneNumber(input.getPhoneNumber() == null ? contact.getPhoneNumber() : input.getPhoneNumber());
            contact.setUpdatedAt(System.currentTimeMillis());
            Contact queryResult = repository.save(contact);
            return new ResponseDataDTO<>(
                    true,
                    true,
                    List.of(queryResult),
                    null,
                    "Contact updated successfully",
                    Collections.emptyList(),
                    null
            );

        } catch (Exception e) {
            if (e instanceof DataAccessException || e instanceof HibernateException) {
                SERVICE_LOGGER.error("Failed to update contact: {}", e.getMessage(), e);
                CONSOLE_LOGGER.error("Failed to update contact: {}", e.getMessage(), e);
                throw new CRUDException("Failed to update contact", fragmentName);
            }
            throw e;
        }
    }

    public ResponseDataDTO<Contact> delete(Long id, String fragmentName) {
        try {
            Optional<Contact> getByIdResult = repository.findById(id);
            if (getByIdResult.isEmpty()) {
                return new ResponseDataDTO<>(
                        false,
                        false,
                        null,
                        null,
                        "Contact not found",
                        List.of("Contact not found"),
                        null
                );
            }
            repository.deleteById(id);
            return new ResponseDataDTO<>(
                    true,
                    true,
                    List.of(getByIdResult.get()),
                    null,
                    "Contact deleted successfully",
                    Collections.emptyList(),
                    null
            );

        } catch (Exception e) {
            if (e instanceof DataAccessException || e instanceof HibernateException) {
                SERVICE_LOGGER.error("Failed to delete contact: {}", e.getMessage(), e);
                CONSOLE_LOGGER.error("Failed to delete contact: {}", e.getMessage(), e);
                throw new CRUDException("Failed to delete contact", fragmentName);
            }
            throw e;
        }
    }
}
