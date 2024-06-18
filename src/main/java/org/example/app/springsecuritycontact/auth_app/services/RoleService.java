package org.example.app.springsecuritycontact.auth_app.services;

import lombok.RequiredArgsConstructor;
import org.example.app.springsecuritycontact.auth_app.entitys.entity.Role;
import org.example.app.springsecuritycontact.auth_app.repository.RoleRepository;
import org.example.app.springsecuritycontact.auth_app.utils.ERole;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public void createUserRole() {
        var role = Role.builder().name(ERole.ROLE_USER.getRoleName()).isSuperUser(false).build();
        roleRepository.save(role);
    }

    public void createAdminRole() {
        var role = Role.builder().name(ERole.ROLE_ADMIN.getRoleName()).isSuperUser(true).build();
        roleRepository.save(role);
    }

    public Optional<Role> getRoleByName (String name) {
        return roleRepository.findByName(name);
    }

}
