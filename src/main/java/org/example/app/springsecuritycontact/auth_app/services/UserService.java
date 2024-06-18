package org.example.app.springsecuritycontact.auth_app.services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.springsecuritycontact.apps_dto.ResponseDataDTO;
import org.example.app.springsecuritycontact.auth_app.entitys.entity.Role;
import org.example.app.springsecuritycontact.auth_app.entitys.entity.User;
import org.example.app.springsecuritycontact.auth_app.repository.UserRepository;
import org.example.app.springsecuritycontact.auth_app.utils.ERole;
import org.example.app.springsecuritycontact.contacts_app.service.ContactsService;
import org.example.app.springsecuritycontact.exceptions.custome_exception.ValidationException;
import org.example.app.springsecuritycontact.validate.auth_form.sign_up.SignUpFormDTO;
import org.example.app.springsecuritycontact.validate.auth_form.sign_up.ValidateSignUpFormProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    private static final Logger AUTH_SERVICE_LOGGER =
            LogManager.getLogger(ContactsService.class);
    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");

    public UserService(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseDataDTO<User> saveUser(SignUpFormDTO form) {
        AUTH_SERVICE_LOGGER.info("Creating new user... {}", form.toString());
        try{
            ValidateSignUpFormProcessing validateSignUpFormProcessing = new ValidateSignUpFormProcessing(userRepository,form);
            CONSOLE_LOGGER.info("Validation result: {}", validateSignUpFormProcessing.getIsValidForm());
            if (!validateSignUpFormProcessing.getIsValidForm()) {
                throw new ValidationException(
                        "Validation error",
                        validateSignUpFormProcessing,
                        "sign_up"
                );
            }
            Optional<Role> role = roleService.getRoleByName(ERole.ROLE_USER.getRoleName());
            if (role.isEmpty()) {
                roleService.createUserRole();
                Optional<Role> createdRole = roleService.getRoleByName(ERole.ROLE_USER.getRoleName());
                if (createdRole.isEmpty()) {
                    throw new RuntimeException("Error while creating user role");
                } else {
                    role = createdRole;
                }
            }
            User user = User.builder()
                    .firstName(form.getFirstName())
                    .lastName(form.getLastName())
                    .email(form.getEmail())
                    .phoneNumber(form.getPhoneNumber())
                    .passwordHash(passwordEncoder.encode(form.getPassword()))
                    .roles(List.of(role.get()))
                    .build();

            User createdUser = userRepository.save(user);
            return new ResponseDataDTO<>(
                    true,
                    true,
                    List.of(createdUser),
                    form,
                    "Registration successful",
                    null,
                    new HashMap<>());
        } catch (Exception e) {
            AUTH_SERVICE_LOGGER.error(String.format("Error while saving user: %s ", e.getMessage()));
            CONSOLE_LOGGER.error(String.format("Error while saving user: %s ", e.getMessage()));
            if (e instanceof ValidationException) {
                throw e;
            }
            throw new RuntimeException("Error while saving user");
        }
    }




}
