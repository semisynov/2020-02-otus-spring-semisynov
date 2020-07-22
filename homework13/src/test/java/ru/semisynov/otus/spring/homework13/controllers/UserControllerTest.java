package ru.semisynov.otus.spring.homework13.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.semisynov.otus.spring.homework13.config.ApplicationConfig;
import ru.semisynov.otus.spring.homework13.model.User;
import ru.semisynov.otus.spring.homework13.model.enums.UserRole;
import ru.semisynov.otus.spring.homework13.repositories.UserRepository;
import ru.semisynov.otus.spring.homework13.security.SecurityUserDetailsService;
import ru.semisynov.otus.spring.homework13.security.config.WebSecurityConfiguration;
import ru.semisynov.otus.spring.homework13.services.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({UserController.class, WebSecurityConfiguration.class, SecurityUserDetailsService.class, ApplicationConfig.class})
@DisplayName("Класс GenreController ")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_LOGIN = "Test";
    private static final String EXPECTED_PASSWORD = "$2a$10$V3blUuumPK12nv0fykUDbu12uJ/oQsBTDA76arlVbfw3Bd429RIHa";
    private static final User EXPECTED_ENTITY = new User(EXPECTED_ID, EXPECTED_LOGIN, false, LocalDateTime.MAX, EXPECTED_PASSWORD, UserRole.ROLE_USER);

    @Test
    @SneakyThrows
    @DisplayName("выводит view списка пользователей")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldReturnUsersList() {
        List<User> users = List.of(EXPECTED_ENTITY);
        given(userService.findAllUsers()).willReturn(users);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("users", users))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("не выводит view списка пользователей не админам")
    @WithMockUser
    public void shouldNotReturnUsersList() {
        List<User> users = List.of(EXPECTED_ENTITY);
        given(userService.findAllUsers()).willReturn(users);

        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    @DisplayName("выводит view редактирования пользователя")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldReturnUserEdit() {
        given(userService.findUserById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);

        mockMvc.perform(get("/user/edit?id=" + EXPECTED_ID))
                .andDo(print())
                .andExpect(view().name("user/edit"))
                .andExpect(model().attribute("user", EXPECTED_ENTITY))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("выводит view просмотра пользователя")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldReturnUserView() {
        given(userService.findUserById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);

        mockMvc.perform(get("/user/view?id=" + EXPECTED_ID))
                .andDo(print())
                .andExpect(view().name("user/view"))
                .andExpect(model().attribute("user", EXPECTED_ENTITY))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("удаляет пользователя и переадресовывает в список")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldDeleteUser() throws Exception {
        doNothing().when(userService).deleteUserById(EXPECTED_ID);

        mockMvc.perform(get("/user/delete?id=1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"));
    }
}