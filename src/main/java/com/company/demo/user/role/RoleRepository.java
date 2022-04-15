package com.company.demo.user.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<AppRole, Short> {
    Optional<AppRole> findRoleByName(String name);
}
