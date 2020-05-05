package ru.semisynov.otus.spring.homework08.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.semisynov.otus.spring.homework08.model.Author;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.model.Comment;
import ru.semisynov.otus.spring.homework08.model.Genre;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBChangeLog {

    private final List<Author> authors = new ArrayList<>();
    private final List<Genre> genres = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();

    @ChangeSet(order = "000", id = "drop", author = "Ilia Semisynov", runAlways = true)
    public void drop(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "Ilia Semisynov", runAlways = true)
    public void initAuthors(MongoTemplate template) {
        for (int i = 0; i < 10; i++) {
            Author author = new Author("Автор" + i);
            template.save(author);
            authors.add(author);
        }
    }

    @ChangeSet(order = "002", id = "initGenres", author = "Ilia Semisynov", runAlways = true)
    public void initGenres(MongoTemplate template) {
        for (int i = 0; i < 10; i++) {
            Genre genre = new Genre("Жанр" + i);
            template.save(genre);
            genres.add(genre);
        }
    }

    @ChangeSet(order = "003", id = "initBooks", author = "Ilia Semisynov", runAlways = true)
    public void initBooks(MongoTemplate template) {
        for (int i = 0; i < 5; i++) {
            List<Author> bookAuthor = List.of(authors.get(i), authors.get(i + 1));
            List<Genre> bookGenres = List.of(genres.get(i), genres.get(i + 1));
            Book book = new Book("Книга" + i, bookAuthor, bookGenres);
            template.save(book);
            books.add(book);
        }
    }

    @ChangeSet(order = "004", id = "initComments", author = "Ilia Semisynov", runAlways = true)
    public void initComments(MongoTemplate template) {
        for (Book book : books) {
            Comment comment = new Comment("Комментарий к книге " + book.getTitle(), book);
            template.save(comment);
        }
    }
}