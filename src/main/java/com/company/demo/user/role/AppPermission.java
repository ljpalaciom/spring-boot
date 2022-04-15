package com.company.demo.user.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "permissions")
public class AppPermission {
    @Id
    @SequenceGenerator(name = "permissions_gen", sequenceName = "permissions_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_gen")
    @Column(updatable = false)
    private Short id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String name;

    public AppPermission(String name) {
        this.name = name;
    }
}
