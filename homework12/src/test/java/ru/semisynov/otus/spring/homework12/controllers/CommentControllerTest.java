package ru.semisynov.otus.spring.homework12.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.semisynov.otus.spring.homework12.config.ApplicationConfig;
import ru.semisynov.otus.spring.homework12.model.Book;
import ru.semisynov.otus.spring.homework12.model.Comment;
import ru.semisynov.otus.spring.homework12.repositories.UserRepository;
import ru.semisynov.otus.spring.homework12.security.SecurityConfiguration;
import ru.semisynov.otus.spring.homework12.security.SecurityUserDetailsService;
import ru.semisynov.otus.spring.homework12.services.CommentService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({CommentController.class, SecurityConfiguration.class, SecurityUserDetailsService.class, ApplicationConfig.class})
@DisplayName("Класс CommentController ")
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private UserRepository userRepository;

    private static final long BOOK_ID = 1L;

    private Book testBook;
    private Comment testComment;

    @BeforeEach
    void setUp() {
        testComment = new Comment();

        testBook = new Book();
        testBook.setId(BOOK_ID);
    }

    @Test
    @SneakyThrows
    @DisplayName("выводит view добавления комментария ")
    @WithMockUser
    public void shouldReturnCommentEdit() {
        given(commentService.addComment(testComment, BOOK_ID)).willReturn(testComment);

        mockMvc.perform(get("/comment/add?bookId=" + BOOK_ID))
                .andDo(print())
                .andExpect(view().name("comment/edit"))
                .andExpect(model().attribute("comment", testComment))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("не выводит view добавления комментария и перекидывает на логин")
    public void shouldNotReturnCommentEdit() {
        given(commentService.addComment(testComment, BOOK_ID)).willReturn(testComment);

        mockMvc.perform(get("/comment/add?bookId=" + BOOK_ID))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }
}