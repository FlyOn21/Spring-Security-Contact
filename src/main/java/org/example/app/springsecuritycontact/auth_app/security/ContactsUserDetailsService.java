package org.example.app.springsecuritycontact.auth_app.security;

import org.example.app.springsecuritycontact.auth_app.entitys.entity.User;
import org.example.app.springsecuritycontact.auth_app.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ContactsUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ContactsUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("Invalid login or password.");
        }
    }
}

