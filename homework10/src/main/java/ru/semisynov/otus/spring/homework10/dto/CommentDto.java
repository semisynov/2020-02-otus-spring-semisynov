package ru.semisynov.otus.spring.homework10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {

    private long id = -1;
    private LocalDateTime dateTime;
    private String text;
}
