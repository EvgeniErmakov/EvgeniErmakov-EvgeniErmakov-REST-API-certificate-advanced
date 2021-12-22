package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.entity.User;
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
}
