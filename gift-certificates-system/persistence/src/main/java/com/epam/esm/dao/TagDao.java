package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.Optional;

/**
 * Interface {@code TagDao} describes abstract behavior for working with tag table in database.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
public interface TagDao extends CrudDao<Tag> {

    /**
     * Find tag by name in database.
     *
     * @param name the name of tag to find
     * @return the optional of found tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Remove tag from gift_certificate_has_tag cross table.
     *
     * @param id the id of tag to remove from cross table
     */
    void removeGiftCertificateHasTag(long id);

    /**
     * Find the most popular tag of user in database.
     *
     * @param userId the user id to find most popular tag
     * @return the optional of found tag
     */
    Optional<Tag> findMostPopularOfUser(long userId);
}
