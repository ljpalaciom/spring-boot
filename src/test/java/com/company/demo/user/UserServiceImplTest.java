package com.company.demo.user;

import com.company.demo.user.role.PermissionRepository;
import com.company.demo.user.role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceImplTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, roleRepository, permissionRepository, passwordEncoder, messageSource);
    }

    @Test
    void saveUserCorrectly() {
        //given
        AppUser user = new AppUser("Arnolfo", "arnolfo", "rodrigo");
        given(userRepository.save(user)).willReturn(user);
        String passwordEncoded = "passwordEncoded";
        given(passwordEncoder.encode(anyString())).willReturn(passwordEncoded);
        //when
        userService.saveUser(user);
        //then
        ArgumentCaptor<AppUser> appUserArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository, times(1)).save(appUserArgumentCaptor.capture());
        AppUser capturedUser = appUserArgumentCaptor.getValue();
        Assertions.assertThat(capturedUser).isEqualTo(user);
        Assertions.assertThat(capturedUser.getPassword()).isEqualTo(passwordEncoded);
    }
}