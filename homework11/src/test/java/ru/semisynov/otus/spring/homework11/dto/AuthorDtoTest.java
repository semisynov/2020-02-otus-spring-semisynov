package ru.semisynov.otus.spring.homework11.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import ru.semisynov.otus.spring.homework11.model.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс AuthorDtoTest ")
class AuthorDtoTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private static final String EXPECTED_ID = "12345";
    private static final String EXPECTED_NAME = "Test";

    @Test
    @DisplayName("конвертирует DO в DTO")
    public void correctConvertAuthorToAuthorDto() {
        Author author = new Author();
        author.setId(EXPECTED_ID);
        author.setName(EXPECTED_NAME);

        AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);
        assertEquals(author.getId(), authorDto.getId());
        assertEquals(author.getName(), authorDto.getName());
    }

    @Test
    @DisplayName("конвертирует DTO в DO")
    public void correctConvertAuthorDtoToAuthor() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(EXPECTED_ID);
        authorDto.setName(EXPECTED_NAME);

        Author author = modelMapper.map(authorDto, Author.class);
        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getName(), author.getName());
    }
}