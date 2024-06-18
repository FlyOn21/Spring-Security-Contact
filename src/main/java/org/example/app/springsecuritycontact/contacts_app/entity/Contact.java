package org.example.app.springsecuritycontact.contacts_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.app.springsecuritycontact.auth_app.entitys.entity.User;
import org.example.app.springsecuritycontact.contacts_app.entity.base_entity.BaseEntity;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Component
@Entity
@Table(name = "contacts", indexes = {
        @Index(name = "idx_email", columnList = "email")
})
public class Contact extends BaseEntity {
    @UuidGenerator
    @Column(name = "contact_id", nullable = false, unique = true, updatable = false)
    private UUID contactId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false, foreignKey = @ForeignKey(name = "fk_contact_user"))
    private User owner;

    // Constructors
    public Contact(UUID customerId, String firstName, String lastName, String email, String phoneNumber, User owner) {
        this.contactId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.owner = owner;
    }

    public Contact(String firstName, String lastName, String email, String phoneNumber, User owner) {
        this.contactId = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.owner = owner;
    }

    public Contact(Long id, UUID customerId, String firstName, String lastName, String email, String phoneNumber, User owner) {
        super(id);
        this.contactId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.owner = owner;
    }

    @PrePersist
    protected void onCreate() {
        long currentTime = System.currentTimeMillis();
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
}
