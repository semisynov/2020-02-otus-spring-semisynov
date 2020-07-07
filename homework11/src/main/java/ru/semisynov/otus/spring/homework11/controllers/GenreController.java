package ru.semisynov.otus.spring.homework11.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.semisynov.otus.spring.homework11.dto.GenreDto;
import ru.semisynov.otus.spring.homework11.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework11.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework11.model.Genre;
import ru.semisynov.otus.spring.homework11.repositories.BookRepository;
import ru.semisynov.otus.spring.homework11.repositories.GenreRepository;

import java.util.Comparator;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    private static final String TEXT_NOT_FOUND = "Genre not found";
    private static final String TEXT_REFERENCE = "Unable to delete the genre %s there are links in the database";

    @GetMapping("/genre")
    public Flux<GenreDto> getGenrePage() {
        return genreRepository.findAll()
                .sort(Comparator.comparing(Genre::getTitle))
                .map(this::convertToDto);
    }

    @GetMapping("/genre/{id}")
    public Mono<GenreDto> getGenreById(@PathVariable("id") String id) {
        return genreRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(TEXT_NOT_FOUND)))
                .map(this::convertToDto);
    }

    @PostMapping("/genre")
    public Mono<GenreDto> createGenre(@RequestBody GenreDto genreDto) {
        return genreRepository.save(convertToEntity(genreDto))
                .map(this::convertToDto);
    }

    @PutMapping("/genre/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<GenreDto> updateGenre(@PathVariable("id") String id, @RequestBody GenreDto genreDto) {
        return genreRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(TEXT_NOT_FOUND)))
                .flatMap(g -> genreRepository.save(convertToEntity(genreDto)))
                .map(this::convertToDto);
    }

    @DeleteMapping("/genre/{id}")
    public Mono<ResponseEntity<Object>> deleteGenre(@PathVariable("id") String id) {
        return genreRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(TEXT_NOT_FOUND)))
                .flatMap(genre -> bookRepository.findByGenres(genre).count()
                        .flatMap(count -> {
                            if (count != 0) {
                                return Mono.error(new DataReferenceException(String.format(TEXT_REFERENCE, genre.getId())));
                            } else {
                                return genreRepository.delete(genre).then(Mono.empty());
                            }
                        }));
    }

    private GenreDto convertToDto(Genre genre) {
        return modelMapper.map(genre, GenreDto.class);
    }

    private Genre convertToEntity(GenreDto genreDto) throws ParseException {
        return modelMapper.map(genreDto, Genre.class);
    }
}