package org.example.app.springsecuritycontact.auth_app.utils;

import lombok.Getter;

@Getter
public enum ERole {
    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private final String roleName;
    ERole(String roleName) {
        this.roleName = roleName;
    }

}
