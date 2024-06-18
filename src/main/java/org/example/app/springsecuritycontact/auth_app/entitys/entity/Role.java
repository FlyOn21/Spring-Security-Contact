package org.example.app.springsecuritycontact.auth_app.entitys.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.app.springsecuritycontact.auth_app.entitys.base_entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "isSuperUser", nullable = false)
    private boolean isSuperUser;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

}
