package ru.semisynov.otus.spring.homework06.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;

    @Column(name = "title")
    private String title;

    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 5)
    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 5)
    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY)
    @JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private List<Comment> comments;

    @Override
    public String toString() {
        return "Book. Id=" + id + "Title=" + title + ", authors=" + authors.stream().map(Author::getName).collect(Collectors.joining(",")).trim();
    }
}