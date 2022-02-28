package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Page;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderDAOImpl implements OrderDAO {

    private static final String SELECT_ALL = "SELECT orders FROM gift_order orders";
    private final EntityManager entityManager;

    @Override
    public BigInteger getCountOfCertificate() {
        return null;
    }

    @Override
    public List<Order> findAll(Page page) {
        return entityManager.createQuery(SELECT_ALL, Order.class)
            .setFirstResult((page.getPage() * page.getSize()) - page.getSize())
            .setMaxResults(page.getSize())
            .getResultList();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public void delete(Order order) {
    }
}
