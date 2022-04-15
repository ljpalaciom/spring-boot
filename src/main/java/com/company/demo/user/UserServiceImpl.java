package com.company.demo.user;

import com.company.demo.exception.AppException;
import com.company.demo.exception.RecordNotFoundException;
import com.company.demo.user.role.AppPermission;
import com.company.demo.user.role.AppRole;
import com.company.demo.user.role.PermissionRepository;
import com.company.demo.user.role.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final PasswordEncoder passwordEncoder;

    private final MessageSource messageSource;

    @Override
    public AppUser findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email).orElseThrow(() ->
                new RecordNotFoundException(messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username).orElseThrow(() ->
                new RecordNotFoundException(messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale())));
    }

    public List<AppUser> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    @Transactional
    public AppUser saveUser(AppUser appUser) {
        if (appUser.getEmail() != null && this.userRepository.findUserByEmail(appUser.getEmail()).isPresent()) {
            throw new AppException(messageSource.getMessage("user-email-taken", null, LocaleContextHolder.getLocale()));
        }
        if (this.userRepository.findUserByUsername(appUser.getUsername()).isPresent()) {
            throw new AppException(messageSource.getMessage("user-username-taken", null, LocaleContextHolder.getLocale()));
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return this.userRepository.save(appUser);
    }

    @Override
    @Transactional
    public AppUser updateUser(AppUser appUser) {
        if (appUser.getId() == null || !this.userRepository.existsById(appUser.getId())) {
            throw new AppException("id of previous created user must be included");
        }
        Optional<AppUser> userByEmail = this.userRepository.findUserByEmail(appUser.getEmail());
        if (userByEmail.isPresent() && !userByEmail.get().getId().equals(appUser.getId())) {
            throw new AppException(messageSource.getMessage("user-email-taken", null, LocaleContextHolder.getLocale()));
        }
        return this.userRepository.save(appUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AppRole saveRole(AppRole appRole) {
        return this.roleRepository.save(appRole);
    }

    @Override
    public List<AppRole> getRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    @Transactional
    public void assignRoleToUser(Long userId, String roleName) {
        AppUser user = this.userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException(messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale())));
        AppRole role = this.roleRepository.findRoleByName(roleName).orElseThrow(() -> new RecordNotFoundException(messageSource.getMessage("role-not-found", null, LocaleContextHolder.getLocale())));
        user.getRoles().add(role);
    }

    @Override
    @Transactional
    public void removeRoleToUser(Long userId, String roleName) {
        AppUser user = this.userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException(messageSource.getMessage("role-not-found", null, LocaleContextHolder.getLocale())));
        user.getRoles().removeIf(appRole -> appRole.getName().equals(roleName));
    }

    @Override
    @Transactional
    public void assignPermissionToUser(Long userId, String permissionName) {
        AppUser user = this.userRepository.findById(userId).orElseThrow((() ->
                new RecordNotFoundException(messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale()))));
        AppPermission permission = this.permissionRepository.findPermissionByName(permissionName).orElseThrow(() ->
                new RecordNotFoundException(messageSource.getMessage("role-not-found", null, LocaleContextHolder.getLocale())));
        user.getPermissions().add(permission);
    }

    @Override
    @Transactional
    public void assignPermissionToRole(Short roleId, String permissionName) {
        AppRole role = this.roleRepository.findById(roleId).orElseThrow(() -> new RecordNotFoundException(messageSource.getMessage("role-not-found", null, LocaleContextHolder.getLocale())));
        AppPermission permission = this.permissionRepository.findPermissionByName(permissionName).orElseThrow(() ->
                new RecordNotFoundException(messageSource.getMessage("permission-not-found", null, LocaleContextHolder.getLocale())));
        role.getPermissions().add(permission);
    }

    @Override
    @Transactional
    public void removePermissionToUser(Long userId, String permissionName) {
        AppUser user = this.userRepository.findById(userId).orElseThrow((() ->
                new RecordNotFoundException(messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale()))));
        AppPermission permission = this.permissionRepository.findPermissionByName(permissionName).orElseThrow(() ->
                new RecordNotFoundException(messageSource.getMessage("role-not-found", null, LocaleContextHolder.getLocale())));
        user.getPermissions().removeIf(appPermission -> appPermission.equals(permission));
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Set<AppPermission> getPermissions() {
        return new HashSet<>(permissionRepository.findAll());
    }

    @Override
    @Transactional
    public AppPermission savePermission(AppPermission appPermission) {
        if (this.permissionRepository.findPermissionByName(appPermission.getName()).isPresent()) {
            throw new AppException(messageSource.getMessage("permission-name-taken", null, LocaleContextHolder.getLocale()));
        }
        return permissionRepository.save(appPermission);
    }

    @Override
    @Transactional
    public void deletePermission(Short id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
