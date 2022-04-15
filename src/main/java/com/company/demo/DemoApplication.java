package com.company.demo;

import com.company.demo.user.AppUser;
import com.company.demo.user.UserService;
import com.company.demo.user.role.AppPermission;
import com.company.demo.user.role.AppRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.Set;

@SpringBootApplication
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserService userService, EntityManager entityManager) {
        return args -> {
            for (EntityType<?> entityType : entityManager.getMetamodel().getEntities()) {
                userService.savePermission(new AppPermission(String.format("%s:READ", entityType.getName())));
                userService.savePermission(new AppPermission(String.format("%s:WRITE", entityType.getName())));
            }
            AppRole adminRole = new AppRole("ROLE_ADMIN");
            adminRole.setPermissions(userService.getPermissions());
            userService.saveUser(new AppUser("Luis", "luis", "contra", Set.of(adminRole)));
            AppUser user = new AppUser("Javier", "javi", "contra", Set.of(new AppRole("ROLE_USER")));
            userService.saveUser(user);
            userService.assignPermissionToUser(user.getId(), String.format("%s:READ", user.getClass().getSimpleName()));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
