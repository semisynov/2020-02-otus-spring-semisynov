package ru.semisynov.otus.spring.homework12.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "text")
    private String text;

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    public Comment(String text, Book book) {
        this.dateTime = LocalDateTime.now();
        this.text = text;
        this.book = book;
    }
}