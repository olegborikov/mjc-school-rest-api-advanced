package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.Optional;

/**
 * Interface {@code UserDao} describes abstract behavior for working with user table in database.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
public interface UserDao extends CrudDao<User> {

    /**
     * Find user by highest cost of all orders in database.
     *
     * @return the optional of found user
     */
    Optional<User> findByHighestCostOfAllOrders();
}
