package ru.semisynov.otus.spring.homework11.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import ru.semisynov.otus.spring.homework11.model.Book;
import ru.semisynov.otus.spring.homework11.model.Comment;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс CommentDtoTest ")
class CommentDtoTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private static final String EXPECTED_ID = "12345";
    private static final String EXPECTED_TEXT = "Test";
    private static final Book EXPECTED_BOOK = new Book(EXPECTED_ID, "Test", Collections.emptyList(), Collections.emptyList());

    @Test
    @DisplayName("конвертирует DO в DTO")
    public void correctConvertCommentToCommentDto() {
        Comment comment = new Comment(EXPECTED_ID, LocalDateTime.now(), EXPECTED_TEXT, EXPECTED_BOOK);

        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        assertEquals(comment.getId(), commentDto.getId());
        assertEquals(comment.getText(), commentDto.getText());
        assertEquals(comment.getDateTime(), commentDto.getDateTime());
    }

    @Test
    @DisplayName("конвертирует DTO в DO")
    public void correctConvertCommentDtoToComment() {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(EXPECTED_ID);
        commentDto.setText(EXPECTED_TEXT);
        commentDto.setDateTime(LocalDateTime.now());

        Comment comment = modelMapper.map(commentDto, Comment.class);
        assertEquals(commentDto.getId(), comment.getId());
        assertEquals(commentDto.getText(), comment.getText());
        assertEquals(commentDto.getDateTime(), comment.getDateTime());
    }
}