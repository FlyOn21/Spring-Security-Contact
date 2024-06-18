package org.example.app.springsecuritycontact.auth_app.entitys.base_entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
}
