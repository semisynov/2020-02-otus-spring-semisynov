package ru.semisynov.otus.spring.homework11.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import ru.semisynov.otus.spring.homework11.model.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс GenreDtoTest ")
class GenreDtoTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private static final String EXPECTED_ID = "12345";
    private static final String EXPECTED_TITLE = "Test";

    @Test
    @DisplayName("конвертирует DO в DTO")
    public void correctConvertGenreToGenreDto() {
        Genre genre = new Genre();
        genre.setId(EXPECTED_ID);
        genre.setTitle(EXPECTED_TITLE);

        GenreDto genreDto = modelMapper.map(genre, GenreDto.class);
        assertEquals(genre.getId(), genreDto.getId());
        assertEquals(genre.getTitle(), genreDto.getTitle());
    }

    @Test
    @DisplayName("конвертирует DTO в DO")
    public void correctConvertGenreDtoToGenre() {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(EXPECTED_ID);
        genreDto.setTitle(EXPECTED_TITLE);

        Genre genre = modelMapper.map(genreDto, Genre.class);
        assertEquals(genreDto.getId(), genre.getId());
        assertEquals(genreDto.getTitle(), genre.getTitle());
    }
}