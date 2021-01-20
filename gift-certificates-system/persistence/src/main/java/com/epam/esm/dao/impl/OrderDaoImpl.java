package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    private static final String ADD = "INSERT INTO `order` (order_price, order_create_date, user_id_fk, "
            + "gift_certificate_id_fk) VALUES (?, ?, ?, ?)";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order add(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setBigDecimal(1, order.getPrice());
            statement.setObject(2, order.getCreateDate());
            statement.setLong(3, order.getUser().getId());
            statement.setLong(4, order.getGiftCertificate().getId());
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            order.setId(keyHolder.getKey().longValue());
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        throw new UnsupportedOperationException("Method findAll is unavailable for OrderDaoImpl");
    }

    @Override
    public Optional<Order> findById(long id) {
        throw new UnsupportedOperationException("Method findById is unavailable for OrderDaoImpl");
    }

    @Override
    public Order update(Order order) {
        throw new UnsupportedOperationException("Method update is unavailable for OrderDaoImpl");
    }

    @Override
    public void remove(long id) {
        throw new UnsupportedOperationException("Method remove is unavailable for OrderDaoImpl");
    }
}
