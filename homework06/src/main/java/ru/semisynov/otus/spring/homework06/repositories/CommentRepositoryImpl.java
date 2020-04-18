package ru.semisynov.otus.spring.homework06.repositories;

import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework06.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository("commentRepository")
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(c) " +
                "from Comment c ", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> typedQuery = em.createQuery("select c " +
                "from Comment c ", Comment.class);
        return typedQuery.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Comment where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Comment> findAllByBook(long bookId) {
        TypedQuery<Comment> typedQuery = em.createQuery("select c " +
                "from Comment c join fetch c.book where c.book.id = :bookId", Comment.class);
        typedQuery.setParameter("bookId", bookId);
        return typedQuery.getResultList();
    }
}
