package ru.semisynov.otus.spring.homework14.test;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.semisynov.otus.spring.homework14.model.Author;
import ru.semisynov.otus.spring.homework14.model.Book;
import ru.semisynov.otus.spring.homework14.model.Comment;
import ru.semisynov.otus.spring.homework14.model.Genre;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBTestChangeLog {

    private final List<Author> authors = new ArrayList<>();
    private final List<Genre> genres = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();

    @ChangeSet(order = "000", id = "drop", author = "Semisynov", runAlways = true)
    public void drop(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "Semisynov", runAlways = true)
    public void initAuthors(MongoTemplate template) {
        for (int i = 0; i < 5; i++) {
            Author author = new Author(String.valueOf(i), "ТестАвтор" + i);
            template.save(author);
            authors.add(author);
        }
    }

    @ChangeSet(order = "002", id = "initGenres", author = "Semisynov", runAlways = true)
    public void initGenres(MongoTemplate template) {
        for (int i = 0; i < 5; i++) {
            Genre genre = new Genre(String.valueOf(i), "ТестЖанр" + i);
            template.save(genre);
            genres.add(genre);
        }
    }

    @ChangeSet(order = "003", id = "initBooks", author = "Semisynov", runAlways = true)
    public void initBooks(MongoTemplate template) {
        for (int i = 0; i < 5; i++) {
            List<Author> bookAuthors = List.of(authors.get(i));
            List<Genre> bookGenres = List.of(genres.get(i));
            Book book = new Book(String.valueOf(i), "ТестКнига" + i, bookAuthors, bookGenres);
            template.save(book);
            books.add(book);
        }
    }

    @ChangeSet(order = "004", id = "initComments", author = "Semisynov", runAlways = true)
    public void initComments(MongoTemplate template) {
        for (Book book : books) {
            Comment comment = new Comment(book, "ТестКоммент" + book.getId());
            template.save(comment);
        }
    }
}
