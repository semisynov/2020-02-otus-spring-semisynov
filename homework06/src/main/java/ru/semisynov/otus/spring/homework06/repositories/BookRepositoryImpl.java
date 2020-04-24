package ru.semisynov.otus.spring.homework06.repositories;

import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework06.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository("bookRepository")
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("select count(b) " +
                "from Book b ", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> typedQuery = entityManager.createQuery("select b " +
                "from Book b ", Book.class);
        return typedQuery.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public void delete(Book book) {
        entityManager.remove(book);
    }
}