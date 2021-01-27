package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.util.Page;

import java.util.List;

/**
 * Interface {@code OrderDao} describes abstract behavior for working with gift_certificate_order table in database.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
public interface OrderDao extends CrudDao<Order> {

    /**
     * Find order by user id in database.
     *
     * @param userId the user id to find order
     * @param page   the page containing information about pagination
     * @return the list of found orders
     */
    List<Order> findByUserId(long userId, Page page);
}
