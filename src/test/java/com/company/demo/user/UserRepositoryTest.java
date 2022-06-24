package com.company.demo.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByNameContaining() {
        //given
        AppUser appUser = new AppUser("Roberto Palacio", "ramiro", "passwordEncoded");
        userRepository.save(appUser);
        //when
        Optional<AppUser> appUserOptional = userRepository.findUserByNameContaining("Roberto");
        //then
        Assertions.assertThat(appUserOptional).as("User not found").isPresent();
        Assertions.assertThat(appUser.getName()).isEqualTo(appUserOptional.get().getName());
    }

    @Test
    void countRoles() {
        List<Object[]> count = userRepository.countRoles();
        //Add users here
        Assertions.assertThat(count).hasSizeGreaterThan(1);
    }
}