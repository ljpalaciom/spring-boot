package com.company.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findUserByEmail(String email);

    Optional<AppUser> findUserByUsername(String username);
}
