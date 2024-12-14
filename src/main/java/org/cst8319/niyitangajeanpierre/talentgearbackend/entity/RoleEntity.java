package org.cst8319.niyitangajeanpierre.talentgearbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)  // Ensure role names are unique
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore  // to prevent infinite recursion into users
    private Set<UserEntity> users;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
