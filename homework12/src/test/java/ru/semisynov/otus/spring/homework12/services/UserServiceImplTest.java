package ru.semisynov.otus.spring.homework12.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.semisynov.otus.spring.homework12.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework12.model.User;
import ru.semisynov.otus.spring.homework12.model.enums.UserRole;
import ru.semisynov.otus.spring.homework12.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Класс UserServiceImpl ")
class UserServiceImplTest {

    @Configuration
    static class TestConfig {

        @Bean
        public UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            return new UserServiceImpl(userRepository, passwordEncoder);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_LOGIN = "Test";
    private static final String EXPECTED_PASSWORD = "$2a$10$V3blUuumPK12nv0fykUDbu12uJ/oQsBTDA76arlVbfw3Bd429RIHa";
    private static final User EXPECTED_ENTITY = new User(EXPECTED_ID, EXPECTED_LOGIN, false, LocalDateTime.MAX, EXPECTED_PASSWORD, UserRole.ROLE_USER);

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("возвращает заданного пользователя по его id")
    void shouldReturnExpectedUserById() {
        when(userRepository.findById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTITY));
        User result = userService.findUserById(EXPECTED_ID);

        assertThat(result).isEqualToComparingFieldByField(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("возвращает ошибку поиска пользователя по его id")
    void shouldReturnItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> userService.findUserById(2L));
    }

    @Test
    @DisplayName("сохраняет пользователя в БД")
    void shouldCreateUser() {
        User testUser = new User(EXPECTED_ID, EXPECTED_LOGIN, false, LocalDateTime.MAX, EXPECTED_PASSWORD, UserRole.ROLE_USER);
        when(userRepository.save(any())).thenReturn(testUser);
        User result = userService.saveUser(testUser);

        assertThat(result).isEqualToComparingFieldByField(testUser);
    }

    @Test
    @DisplayName("возвращает всех пользователей")
    void shouldReturnAllUsers() {
        List<User> users = List.of(EXPECTED_ENTITY);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAllUsers();

        assertThat(result).isNotNull().hasSize(users.size())
                .allMatch(u -> !u.getLogin().equals(""));
    }

    @Test
    @DisplayName("удаляет пользователя из БД")
    void shouldDeleteUser() {
        User testUser = new User(5L, EXPECTED_LOGIN, false, LocalDateTime.MAX, EXPECTED_PASSWORD, UserRole.ROLE_USER);
        when(userRepository.findById(5L)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(testUser);

        assertDoesNotThrow(() -> userService.deleteUserById(5L));
    }
}