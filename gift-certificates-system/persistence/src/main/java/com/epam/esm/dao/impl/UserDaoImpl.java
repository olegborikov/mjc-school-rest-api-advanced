package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.util.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Class {@code UserDaoImpl} is implementation of interface {@link UserDao} and intended to work with user table.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final String FIND_ALL = "SELECT u FROM User u";
    private static final String FIND_BY_HIGHEST_COST_OF_ALL_ORDERS = "SELECT u FROM User u WHERE u.id IN " +
            "(SELECT o.userId FROM Order o GROUP BY o.userId ORDER BY SUM(o.price) DESC)";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User add(User user) {
        throw new UnsupportedOperationException("Method add is unavailable for UserDaoImpl");
    }

    @Override
    public List<User> findAll(Page page) {
        return entityManager.createQuery(FIND_ALL, User.class)
                .setFirstResult((page.getNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public User update(User user) {
        throw new UnsupportedOperationException("Method update is unavailable for UserDaoImpl");
    }

    @Override
    public void remove(long id) {
        throw new UnsupportedOperationException("Method remove is unavailable for UserDaoImpl");
    }

    @Override
    public Optional<User> findByHighestCostOfAllOrders() {
        return entityManager.createQuery(FIND_BY_HIGHEST_COST_OF_ALL_ORDERS, User.class)
                .setMaxResults(1)
                .getResultList().stream()
                .findFirst();
    }
}
