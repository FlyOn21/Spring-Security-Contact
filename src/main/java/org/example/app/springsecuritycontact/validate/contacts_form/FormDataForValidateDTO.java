package org.example.app.springsecuritycontact.validate.contacts_form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDataForValidateDTO {
    private Long id;
    private UUID contactId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

}
