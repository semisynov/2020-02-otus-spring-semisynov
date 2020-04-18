package ru.semisynov.otus.spring.homework06.dao;

import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework06.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
        query.executeUpdate();
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