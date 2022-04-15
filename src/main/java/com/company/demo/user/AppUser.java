package com.company.demo.user;

import com.company.demo.user.role.AppPermission;
import com.company.demo.user.role.AppRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class AppUser implements UserDetails {

    @Id
    @SequenceGenerator(name = "app_users_gen", sequenceName = "users_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_users_gen")
    @Column(updatable = false)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Size(min = 5)
    @Column(nullable = false)
    private String password;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    @Email
    @Column(unique = true)
    private String email;

    private Gender gender;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dayBirth;

    @Transient
    private Integer age;

    //TODO how does work cascade in many to many
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles_detail",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<AppRole> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_permissions_detail",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    Set<AppPermission> permissions;

    public AppUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.permissions = new HashSet<>();
    }

    public AppUser(String name, String username, String password, Set<AppRole> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.permissions = new HashSet<>();
    }

    public Integer getAge() {
        if (this.dayBirth == null) {
            return null;
        }
        return Period.between(this.dayBirth, LocalDate.now()).getYears();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.addAll(this.getRoles().stream().map(appRole -> new SimpleGrantedAuthority(appRole.getName())).collect(Collectors.toList()));
        authorityList.addAll(this.getPermissions().stream().map(appPermission -> new SimpleGrantedAuthority(appPermission.getName())).collect(Collectors.toList()));
        return authorityList;
    }
}
