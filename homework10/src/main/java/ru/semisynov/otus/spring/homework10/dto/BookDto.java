package ru.semisynov.otus.spring.homework10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDto {

    private long id = -1;
    private String title;
    private List<AuthorDto> authors;
    private List<GenreDto> genres;
    private List<CommentDto> comments;
}
