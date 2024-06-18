package org.example.app.springsecuritycontact.config;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.app.springsecuritycontact.auth_app.security.ContactsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ContactsUserDetailsService contactsUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/js/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .requestMatchers("/icons/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/auth/signIn")
                                .loginProcessingUrl("/auth/signIn")
                                .successHandler((request, response, authentication) -> {
                                    HttpSession session = request.getSession();
                                    session.setAttribute("user", authentication.getPrincipal());
                                    session.setAttribute("isCurrentUser", "true");
                                    response.sendRedirect("/contacts");
                                }
                                )
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/signOut", "GET"))
                                .addLogoutHandler((request, response, authentication) -> {
                                    HttpSession session = request.getSession();
                                    session.removeAttribute("user");
                                    session.removeAttribute("isCurrentUser");
                                    session.setAttribute("logoutMsg", "You are logged out");
                                    try {
                                        response.sendRedirect("/");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(contactsUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
