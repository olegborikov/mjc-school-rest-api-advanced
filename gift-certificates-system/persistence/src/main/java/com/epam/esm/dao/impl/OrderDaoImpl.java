package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    private static final String FIND_BY_USER_ID = "SELECT o FROM Order o WHERE o.userId = :user_id";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order add(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public List<Order> findAll() {
        throw new UnsupportedOperationException("Method findAll is unavailable for OrderDaoImpl");
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public Order update(Order order) {
        throw new UnsupportedOperationException("Method update is unavailable for OrderDaoImpl");
    }

    @Override
    public void remove(long id) {
        throw new UnsupportedOperationException("Method remove is unavailable for OrderDaoImpl");
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return entityManager.createQuery(FIND_BY_USER_ID, Order.class)
                .setParameter("user_id", userId)
                .getResultList();
    }
}
