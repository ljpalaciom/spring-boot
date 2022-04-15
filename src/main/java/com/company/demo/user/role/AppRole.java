package com.company.demo.user.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public class AppRole {

    @Id
    @SequenceGenerator(name = "roles_generator", sequenceName = "roles_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_generator")
    @Column(updatable = false)
    private Short id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "roles_permissions_detail",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    Set<AppPermission> permissions;

    public AppRole(String name) {
        this.name = name;
    }
}
