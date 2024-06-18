package org.example.app.springsecuritycontact.auth_app.entitys.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.app.springsecuritycontact.auth_app.entitys.base_entity.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_email", columnList = "email")
})
public class User extends BaseEntity implements UserDetails, Serializable {

    @Column(name = "contact_id", nullable = false, unique = true, updatable = false)
    private UUID contactId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;


    @PrePersist
    protected void onCreate() {
        long currentTime = System.currentTimeMillis();
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
        this.contactId = UUID.randomUUID();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
