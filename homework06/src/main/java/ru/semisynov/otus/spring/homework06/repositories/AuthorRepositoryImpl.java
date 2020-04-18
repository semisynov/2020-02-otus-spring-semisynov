package ru.semisynov.otus.spring.homework06.repositories;

import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework06.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework06.model.Author;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository("authorRepository")
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(a) " +
                "from Author a ", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> typedQuery = em.createQuery("select a " +
                "from Author a ", Author.class);
        return typedQuery.getResultList();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Author where id = :id");
        query.setParameter("id", id);
        try {
            query.executeUpdate();
        } catch (PersistenceException e) {
            throw new DataReferenceException(String.format("Unable to delete the author %s there are links in the database", id));
        }
    }

    @Override
    public Optional<Author> findByName(String name) {
        TypedQuery<Author> typedQuery = em.createQuery(
                "select a " +
                        "from Author a " +
                        "where upper(a.name) = upper(:name)"
                , Author.class);
        typedQuery.setParameter("name", name);
        return Optional.ofNullable(typedQuery.getSingleResult());
    }
}