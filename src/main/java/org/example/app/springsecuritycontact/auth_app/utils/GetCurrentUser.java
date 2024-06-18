package org.example.app.springsecuritycontact.auth_app.utils;

import org.example.app.springsecuritycontact.auth_app.entitys.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class GetCurrentUser {
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
