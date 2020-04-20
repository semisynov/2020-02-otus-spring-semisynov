package ru.semisynov.otus.spring.homework06.repositories;

import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework06.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository("commentRepository")
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("select count(c) " +
                "from Comment c ", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> typedQuery = entityManager.createQuery("select c " +
                "from Comment c join fetch c.book", Comment.class);
        return typedQuery.getResultList();
    }

    @Override
    public void add(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public void delete(Comment comment) {
        entityManager.remove(comment);
    }
}
