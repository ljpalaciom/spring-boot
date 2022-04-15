package com.company.demo.user.role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RoleToUserForm {

    @NotNull
    private Long id;

    @NotBlank
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
