package ru.semisynov.otus.spring.homework10.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.semisynov.otus.spring.homework10.dto.AuthorDto;
import ru.semisynov.otus.spring.homework10.model.Author;
import ru.semisynov.otus.spring.homework10.services.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final ModelMapper modelMapper;

    @GetMapping("/author")
    public ResponseEntity<List<AuthorDto>> getAuthorPage() {
        List<AuthorDto> authors = authorService.findAllAuthors().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") long id) {
        AuthorDto authorDto = convertToDto(authorService.findAuthorById(id));
        return ResponseEntity.ok(authorDto);
    }

    @PostMapping("/author")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        Author author = convertToEntity(authorDto);
        Author postCreated = authorService.saveAuthor(author);
        return ResponseEntity.ok(convertToDto(postCreated));
    }

    @PutMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto updateAuthor(@PathVariable("id") long id, @RequestBody AuthorDto authorDto) {
        Author author = convertToEntity(authorDto);
        return convertToDto(authorService.updateAuthor(id, author));
    }

    @DeleteMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthor(@PathVariable("id") long id) {
        authorService.deleteAuthorById(id);
    }

    private AuthorDto convertToDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    private Author convertToEntity(AuthorDto authorDto) throws ParseException {
        return modelMapper.map(authorDto, Author.class);
    }
}