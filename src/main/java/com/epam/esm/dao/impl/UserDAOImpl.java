package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.entity.User;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;
    private static final String SELECT_ALL_USERS = "SELECT a FROM clientele a";
    private static final String SQL_FIND_USER_USE_LOGIN = "select u from clientele u where u.login=:login";

    @Override
    public List<User> findAll(Page page) {
        return entityManager.createQuery(SELECT_ALL_USERS, User.class)
            .setFirstResult((page.getPage() * page.getSize()) - page.getSize())
            .setMaxResults(page.getSize())
            .getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public User create(User user) {
        throw new UnsupportedOperationException("method not supported yet");
    }

    @Override
    public void delete(User user) {
        throw new UnsupportedOperationException("method not supported yet");
    }

    public Optional<User> findUserByLogin(String login) {
        TypedQuery<User> query = entityManager.createQuery(SQL_FIND_USER_USE_LOGIN, User.class);
        query.setParameter("login", login);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
