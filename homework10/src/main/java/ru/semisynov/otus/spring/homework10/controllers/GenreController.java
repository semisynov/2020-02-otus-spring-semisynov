package ru.semisynov.otus.spring.homework10.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.semisynov.otus.spring.homework10.dto.GenreDto;
import ru.semisynov.otus.spring.homework10.model.Genre;
import ru.semisynov.otus.spring.homework10.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController("genreController")
public class GenreController {

    private final GenreService genreService;
    private final ModelMapper modelMapper;

    @GetMapping("/genre")
    public ResponseEntity<List<GenreDto>> getGenrePage() {
        List<GenreDto> genres = genreService.findAllGenres().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/genre/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable("id") long id) {
        GenreDto genreDto = convertToDto(genreService.findGenreById(id));
        return ResponseEntity.ok(genreDto);
    }

    @PostMapping("/genre")
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreDto genreDto) {
        Genre genre = convertToEntity(genreDto);
        Genre postCreated = genreService.saveGenre(genre);
        return ResponseEntity.ok(convertToDto(postCreated));
    }

    @PutMapping("/genre/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GenreDto updateGenre(@PathVariable("id") long id, @RequestBody GenreDto genreDto) {
        Genre genre = convertToEntity(genreDto);
        return convertToDto(genreService.updateGenre(id, genre));
    }

    @DeleteMapping("/genre/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGenre(@PathVariable("id") long id) {
        genreService.deleteGenreById(id);
    }

    private GenreDto convertToDto(Genre genre) {
        return modelMapper.map(genre, GenreDto.class);
    }

    private Genre convertToEntity(GenreDto genreDto) throws ParseException {
        return modelMapper.map(genreDto, Genre.class);
    }
}