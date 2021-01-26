package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.Optional;

public interface UserDao extends CrudDao<User> {
    Optional<User> findByHighestCostOfAllOrders();
}
