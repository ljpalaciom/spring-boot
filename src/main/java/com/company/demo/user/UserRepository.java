package com.company.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findUserByEmail(String email);

    Optional<AppUser> findUserByUsername(String username);

    @Query("SELECT user from AppUser user WHERE user.name LIKE CONCAT('%', :name,'%')")
    Optional<AppUser> findUserByNameContaining(String name);

    @Query("SELECT user.name, COUNT(roles) from AppUser user JOIN user.roles roles GROUP BY user.id")
    List<Object[]> countRoles();
}
