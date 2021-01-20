package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.mapper.UserMapper;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private static final String FIND_ALL = "SELECT user_id, user_name FROM user";
    private static final String FIND_BY_ID = "SELECT user_id, user_name FROM user WHERE user_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public User add(User user) {
        throw new UnsupportedOperationException("Method add is unavailable for UserDaoImpl");
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL, userMapper);
    }

    @Override
    public Optional<User> findById(long id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, userMapper).stream()
                .findFirst();
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
