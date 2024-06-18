package org.example.app.springsecuritycontact.validate.auth_form.sign_up;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpFormDTO {
    private String password;
    private String passwordConfirm;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
}
