package ru.semisynov.otus.spring.homework06.dao;

import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework06.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository("genreRepository")
public class GenreRepositoryImpl implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(g) " +
                "from Genre g ", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> typedQuery = em.createQuery("select g " +
                "from Genre g ", Genre.class);
        return typedQuery.getResultList();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Genre where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Genre> getByTitle(String title) {
        TypedQuery<Genre> typedQuery = em.createQuery(
                "select g " +
                        "from Genre g " +
                        "where upper(g.title) = upper(:title)"
                , Genre.class);
        typedQuery.setParameter("title", title);
        return Optional.ofNullable(typedQuery.getSingleResult());
    }
}