package org.example.app.springsecuritycontact.validate.auth_form.sign_up;

import org.example.app.springsecuritycontact.auth_app.entitys.entity.User;
import org.example.app.springsecuritycontact.auth_app.repository.UserRepository;
import org.example.app.springsecuritycontact.validate.Validator;
import org.example.app.springsecuritycontact.validate.enums.EValidateAuth;
import org.example.app.springsecuritycontact.validate.enums.IValidateUnit;
import org.example.app.springsecuritycontact.validate.interfaces.IFormDataProcessing;
import org.example.app.springsecuritycontact.validate.validate_entity.ValidateResultDTO;

import java.util.HashMap;
import java.util.Optional;

public class ValidateSignUpFormProcessing implements IFormDataProcessing<SignUpFormDTO> {
    private final HashMap<String, String> validationFormErrors = new HashMap<>();
    private boolean isValidForm = true;
    private final SignUpFormDTO data;
    private final UserRepository repository;


    public ValidateSignUpFormProcessing(UserRepository repository, SignUpFormDTO data) {
        this.repository = repository;
        this.data = data;
        validateFirstName(data.getFirstName());
        validateLastName(data.getLastName());
        validateEmail(data.getEmail());
        validatePhone(data.getPhoneNumber());
        validatePassword(data.getPassword());
        validatePasswordConfirm(data.getPassword(), data.getPasswordConfirm());
    }

    @Override
    public HashMap<String, String> getValidationFormErrors() {
        return validationFormErrors;
    }

    @Override
    public boolean getIsValidForm() {
        return isValidForm;
    }

    @Override
    public SignUpFormDTO getData() {
        return data;
    }



    private ValidateResultDTO validateField(String value, IValidateUnit validationType) {
        Validator<IValidateUnit> validator = new Validator<>();
        if (value==null) {
            ValidateResultDTO answer = new ValidateResultDTO();
            answer.addError(" Is empty (required)");
            return answer;
        }
        return validator.validate(value, validationType);
    }

    private void validateFirstName(String firstName) {
        ValidateResultDTO answer = validateField(firstName, EValidateAuth.FIRST_NAME);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.put("firstName", String.join(", ", answer.getErrorsList()));
        }

    }

    private void validateLastName(String  lastName) {
        ValidateResultDTO answer = validateField(lastName, EValidateAuth.LAST_NAME);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.put("lastName", String.join(", ", answer.getErrorsList()));
        }
    }

    private void validateEmail(String  email) {
        ValidateResultDTO answer = validateField(email, EValidateAuth.EMAIL);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.put("email", String.join(", ", answer.getErrorsList()));
        }
        if (isEmailExists(email)) {
            isValidForm = false;
            answer.addError("Email already exists");
            validationFormErrors.put("email", String.join(", ", answer.getErrorsList()));
        }
    }

    private void validatePhone(String  phone) {
        ValidateResultDTO answer = validateField(phone, EValidateAuth.PHONE);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.put("phoneNumber", String.join(", ", answer.getErrorsList()));
        }
    }

    private void validatePassword(String  password) {
        ValidateResultDTO answer = validateField(password, EValidateAuth.PASSWORD);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.put("password", String.join(", ", answer.getErrorsList()));
        }
    }

    private void validatePasswordConfirm(String  password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            isValidForm = false;
            validationFormErrors.put("passwordConfirm", "Passwords do not match");
        }
    }

    private boolean isEmailExists(String email) {
        Optional<User> contact = repository.findByEmail(email);
        return contact.isPresent();
    }

}
