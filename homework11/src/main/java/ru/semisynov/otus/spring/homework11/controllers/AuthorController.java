package ru.semisynov.otus.spring.homework11.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.semisynov.otus.spring.homework11.dto.AuthorDto;
import ru.semisynov.otus.spring.homework11.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework11.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework11.model.Author;
import ru.semisynov.otus.spring.homework11.repositories.AuthorRepository;
import ru.semisynov.otus.spring.homework11.repositories.BookRepository;

import java.util.Comparator;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    private static final String TEXT_NOT_FOUND = "Author not found";
    private static final String TEXT_REFERENCE = "Unable to delete the author %s there are links in the database";

    @GetMapping("/author")
    public Flux<AuthorDto> getAuthorPage() {
        return authorRepository.findAll()
                .sort(Comparator.comparing(Author::getName))
                .map(this::convertToDto);
    }

    @GetMapping("/author/{id}")
    public Mono<AuthorDto> getAuthorById(@PathVariable("id") String id) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(TEXT_NOT_FOUND)))
                .map(this::convertToDto);
    }

    @PostMapping("/author")
    public Mono<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        return authorRepository.save(convertToEntity(authorDto))
                .map(this::convertToDto);
    }

    @PutMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AuthorDto> updateAuthor(@PathVariable("id") String id, @RequestBody AuthorDto authorDto) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(TEXT_NOT_FOUND)))
                .flatMap(a -> authorRepository.save(convertToEntity(authorDto)))
                .map(this::convertToDto);
    }

    @DeleteMapping("/author/{id}")
    public Mono<ResponseEntity<Object>> deleteAuthor(@PathVariable("id") String id) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(TEXT_NOT_FOUND)))
                .flatMap(author -> bookRepository.findByAuthors(author).count()
                        .flatMap(count -> {
                            if (count != 0) {
                                return Mono.error(new DataReferenceException(String.format(TEXT_REFERENCE, author.getId())));
                            } else {
                                return authorRepository.delete(author).then(Mono.empty());
                            }
                        }));
    }

    private AuthorDto convertToDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    private Author convertToEntity(AuthorDto authorDto) throws ParseException {
        return modelMapper.map(authorDto, Author.class);
    }
}