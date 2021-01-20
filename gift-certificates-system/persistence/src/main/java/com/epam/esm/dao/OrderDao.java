package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderDao extends CrudDao<Order> {

    List<Order> findByUserId(long userId);
}
