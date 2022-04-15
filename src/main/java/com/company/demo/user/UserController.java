package com.company.demo.user;

import com.company.demo.user.role.AppPermission;
import com.company.demo.user.role.AppRole;
import com.company.demo.user.role.RoleToUserForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user")
    public List<AppUser> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{username}")
    public AppUser getUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    //TODO Is a bad practice using entity here instead of a DTO
    @PostMapping("/user")
    public ResponseEntity<AppUser> createUser(@RequestBody @Valid AppUser appUser) {
        return new ResponseEntity<>(userService.saveUser(appUser), HttpStatus.CREATED);
    }

    @PutMapping("/user")
    public ResponseEntity<AppUser> updateUser(@RequestBody @Valid AppUser appUser) {
        return new ResponseEntity<>(userService.updateUser(appUser), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/role")
    public List<AppRole> getRoles() {
        return userService.getRoles();
    }

    @PostMapping("/role")
    public ResponseEntity<AppRole> createRole(@RequestBody @Valid AppRole role) {
        return new ResponseEntity<>(userService.saveRole(role), HttpStatus.CREATED);
    }

    @DeleteMapping("/role/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        userService.deleteRole(id);
    }

    @PostMapping("/role/addtouser")
    public void addRoleToUser(@RequestBody @Valid RoleToUserForm form) {
        userService.assignRoleToUser(form.getId(), form.getRole());
    }

    @GetMapping("/permission")
    public List<AppRole> getPermissions() {
        return userService.getRoles();
    }

    @PostMapping("/permission")
    public ResponseEntity<AppPermission> createPermission(@RequestBody @Valid AppPermission permission) {
        return new ResponseEntity<>(userService.savePermission(permission), HttpStatus.CREATED);
    }

    @DeleteMapping("/permission/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePermission(@PathVariable Short id) {
        userService.deletePermission(id);
    }

    @PostMapping("/permission/addtouser")
    public void addPermissionToUser(@RequestBody @Valid RoleToUserForm form) {
        userService.assignRoleToUser(form.getId(), form.getRole());
    }
}