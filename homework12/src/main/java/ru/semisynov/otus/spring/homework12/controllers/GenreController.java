package ru.semisynov.otus.spring.homework12.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.semisynov.otus.spring.homework12.model.Genre;
import ru.semisynov.otus.spring.homework12.services.GenreService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller("genreController")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    private static final String BAD_ID_PARAMETER = "Genre id can only be a number";

    @GetMapping("/genre")
    public String listView(Model model) {
        List<Genre> genres = genreService.findAllGenres()
                .stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toList());
        model.addAttribute("genres", genres);
        return "genre/list";
    }

    @GetMapping("/genre/edit")
    public String editView(@RequestParam("id") long id, @RequestParam(name = "isCreate", required = false) boolean isCreate, Model model) {
        Genre genre;
        if (!isCreate) {
            genre = genreService.findGenreById(id);
        } else {
            genre = new Genre();
        }
        model.addAttribute("genre", genre);
        return "genre/edit";
    }

    @GetMapping("/genre/view")
    public String viewPage(@RequestParam("id") long id, Model model) {
        Genre genre = genreService.findGenreById(id);
        model.addAttribute("genre", genre);
        return "genre/view";
    }

    @PostMapping("/genre")
    public String postGenre(@ModelAttribute Genre genre) {
        genreService.saveGenre(genre);
        return "redirect:/genre";
    }

    @GetMapping("/genre/delete")
    public String deleteGenre(@RequestParam("id") long id) {
        genreService.deleteGenreById(id);
        return "redirect:/genre";
    }
}