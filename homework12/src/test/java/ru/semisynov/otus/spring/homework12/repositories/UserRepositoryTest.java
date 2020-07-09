package ru.semisynov.otus.spring.homework12.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.semisynov.otus.spring.homework12.model.User;
import ru.semisynov.otus.spring.homework12.model.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с пользователями ")
@DataJpaTest
class UserRepositoryTest {

    private static final int EXPECTED_USER_COUNT = 2;
    private static final long FIRST_ID = 1L;
    private static final String EXPECTED_USER_LOGIN_1 = "testUser";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("возвращает общее количество всех пользователей")
    @Test
    void shouldReturnExpectedUsersCount() {
        long count = userRepository.count();
        assertThat(count).isEqualTo(EXPECTED_USER_COUNT);
    }

    @DisplayName("возвращает заданного пользователя по его id")
    @Test
    void shouldReturnExpectedUserById() {
        Optional<User> optionalUser = userRepository.findById(1L);
        User expectedUser = entityManager.find(User.class, FIRST_ID);
        assertThat(optionalUser).isPresent().get()
                .isEqualToComparingFieldByField(expectedUser);
    }

    @DisplayName("возвращает список всех пользователей ")
    @Test
    void shouldReturnExpectedUsersList() {
        List<User> users = userRepository.findAll();
        assertThat(users).isNotNull().hasSize(EXPECTED_USER_COUNT)
                .allMatch(u -> !u.getLogin().equals(""));
    }

    @DisplayName("добавляет пользователя в БД")
    @Test
    void shouldInsertUser() {
        User testUser = new User("Test", LocalDateTime.MAX, "12345", UserRole.ROLE_USER);
        userRepository.save(testUser);

        assertThat(testUser.getId()).isGreaterThan(0);

        User actualUser = entityManager.find(User.class, testUser.getId());
        assertThat(actualUser).isNotNull().matches(u -> !u.getLogin().isEmpty());
    }

    @DisplayName("удаляет пользователя из БД")
    @Test
    void shouldDeleteUser() {
        User firstUser = entityManager.find(User.class, FIRST_ID);
        assertThat(firstUser).isNotNull();

        userRepository.delete(firstUser);
        User deletedUser = entityManager.find(User.class, FIRST_ID);

        assertThat(deletedUser).isNull();
    }

    @DisplayName("возвращает заданного пользователя по его логину")
    @Test
    void shouldReturnExpectedUserByLogin() {
        Optional<User> optionalUser = userRepository.findByLogin(EXPECTED_USER_LOGIN_1);
        User expectedUser = entityManager.find(User.class, 2L);
        assertThat(optionalUser).isPresent().get()
                .isEqualToComparingFieldByField(expectedUser);
    }
}