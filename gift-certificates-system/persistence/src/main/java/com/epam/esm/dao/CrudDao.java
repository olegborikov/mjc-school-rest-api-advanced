package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code CrudDao} describes CRUD operations for working with database tables.
 *
 * @param <T> the type parameter
 * @author Oleg Borikov
 * @version 1.0
 */
public interface CrudDao<T> {

    /**
     * Add entity to database.
     *
     * @param t the entity to add
     * @return the added entity
     */
    T add(T t);

    /**
     * Find all entities in database.
     *
     * @return the list of found entities
     */
    List<T> findAll();

    /**
     * Find entity in database by id.
     *
     * @param id the id of entity to find
     * @return the optional of found entity
     */
    Optional<T> findById(long id);

    /**
     * Update entity in database.
     *
     * @param t the entity to update
     * @return the updated entity
     */
    T update(T t);

    /**
     * Remove entity from database.
     *
     * @param id the id of entity to remove
     */
    void remove(long id);

    /**
     * Remove entity from gift_certificate_has_tag cross table.
     *
     * @param id the id of entity to remove from cross table
     */
    void removeGiftCertificateHasTag(long id);
}
