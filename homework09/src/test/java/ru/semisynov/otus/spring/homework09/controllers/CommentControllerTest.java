package ru.semisynov.otus.spring.homework09.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.semisynov.otus.spring.homework09.model.Book;
import ru.semisynov.otus.spring.homework09.model.Comment;
import ru.semisynov.otus.spring.homework09.services.CommentService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
@DisplayName("Класс CommentController ")
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    private static final long BOOK_ID = 1L;

    private Book testBook;
    private Comment testComment;

    @BeforeEach
    void setUp() {
        testComment = new Comment();

        testBook = new Book();
        testBook.setId(BOOK_ID);
    }

    @SneakyThrows
    @DisplayName("выводит view добавления комментария ")
    @Test
    public void shouldReturnCommentEdit() {
        given(commentService.addComment(testComment, BOOK_ID)).willReturn(testComment);

        mockMvc.perform(get("/comment/add?bookId=" + BOOK_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("comment/edit"))
                .andExpect(model().attribute("comment", testComment))
                .andExpect(status().isOk());
    }
}