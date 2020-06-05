package ru.semisynov.otus.spring.homework10.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import ru.semisynov.otus.spring.homework10.model.Author;
import ru.semisynov.otus.spring.homework10.model.Book;
import ru.semisynov.otus.spring.homework10.model.Genre;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс BookDtoTest ")
class BookDtoTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_TITLE = "Test";
    private static final List<Author> EXPECTED_AUTHORS = Collections.singletonList(new Author(1L, "Test"));
    private static final List<AuthorDto> EXPECTED_AUTHORS_DTO = Collections.singletonList(new AuthorDto(1L, "Test"));
    private static final List<Genre> EXPECTED_GENRES = Collections.singletonList(new Genre(1L, "Test"));
    private static final List<GenreDto> EXPECTED_GENRES_DTO = Collections.singletonList(new GenreDto(1L, "Test"));

    @Test
    @DisplayName("конвертирует DO в DTO")
    public void correctConvertBookToBookDto() {
        Book book = new Book();
        book.setId(EXPECTED_ID);
        book.setTitle(EXPECTED_TITLE);
        book.setAuthors(EXPECTED_AUTHORS);
        book.setGenres(EXPECTED_GENRES);

        BookDto bookDto = modelMapper.map(book, BookDto.class);
        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertThat(bookDto.getAuthors()).isNotNull().hasSize(EXPECTED_AUTHORS.size())
                .allMatch(a -> !a.getName().equals(""));
        assertThat(bookDto.getGenres()).isNotNull().hasSize(EXPECTED_GENRES.size())
                .allMatch(g -> !g.getTitle().equals(""));
    }

    @Test
    @DisplayName("конвертирует DTO в DO")
    public void correctConvertBookDtoToBook() {
        BookDto bookDto = new BookDto();
        bookDto.setId(EXPECTED_ID);
        bookDto.setTitle(EXPECTED_TITLE);
        bookDto.setAuthors(EXPECTED_AUTHORS_DTO);
        bookDto.setGenres(EXPECTED_GENRES_DTO);

        Book book = modelMapper.map(bookDto, Book.class);
        assertEquals(bookDto.getId(), book.getId());
        assertEquals(bookDto.getTitle(), book.getTitle());
        assertThat(book.getAuthors()).isNotNull().hasSize(EXPECTED_AUTHORS_DTO.size())
                .allMatch(a -> !a.getName().equals(""));
        assertThat(bookDto.getGenres()).isNotNull().hasSize(EXPECTED_GENRES_DTO.size())
                .allMatch(g -> !g.getTitle().equals(""));
    }
}