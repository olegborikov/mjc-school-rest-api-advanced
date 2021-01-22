package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private static final String FIND_ALL = "SELECT u FROM User u";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User add(User user) {
        throw new UnsupportedOperationException("Method add is unavailable for UserDaoImpl");
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery(FIND_ALL, User.class).getResultList();
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
}
