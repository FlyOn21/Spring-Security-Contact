package org.example.app.springsecuritycontact.contacts_app.service.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.app.springsecuritycontact.contacts_app.entity.Contact;
import org.example.app.springsecuritycontact.contacts_app.utils.time.TimeConverter;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ContactDTO {
    private Long id;
    private UUID contactId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String createdAt;
    private String updatedAt;


    public ContactDTO(Contact customer) {
        this.id = customer.getId();
        this.contactId = customer.getContactId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.createdAt = TimeConverter.millisToDateTime(customer.getCreatedAt());
        this.updatedAt = TimeConverter.millisToDateTime(customer.getUpdatedAt());
    }
}
