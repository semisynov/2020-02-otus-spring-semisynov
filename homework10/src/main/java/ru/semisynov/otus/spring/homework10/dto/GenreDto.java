package ru.semisynov.otus.spring.homework10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GenreDto {

    private long id = -1;
    private String title;
}
