package com.company.demo.user;

import com.company.demo.user.role.AppPermission;
import com.company.demo.user.role.AppRole;

import java.util.List;
import java.util.Set;

public interface UserService {
    //TODO this kind of things requires pagination
    List<AppUser> getUsers();

    AppUser findUserByEmail(String email);

    AppUser findUserByUsername(String username);

    AppUser saveUser(AppUser appUser);

    AppUser updateUser(AppUser appUser);

    void deleteUser(Long id);

    List<AppRole> getRoles();

    AppRole saveRole(AppRole appRole);

    void deleteRole(Long id);

    Set<AppPermission> getPermissions();

    AppPermission savePermission(AppPermission appPermission);

    void deletePermission(Short id);

    void assignRoleToUser(Long userId, String roleName);

    void removeRoleToUser(Long userId, String roleName);

    void assignPermissionToUser(Long userId, String permissionName);

    void assignPermissionToRole(Short roleId, String permissionName);

    void removePermissionToUser(Long id, String permissionName);
}
