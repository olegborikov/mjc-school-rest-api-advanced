package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code TagDao} describes abstract behavior for working with tag table in database.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
public interface TagDao extends CrudDao<Tag> {

    /**
     * Find tags by gift certificate id in database.
     *
     * @param giftCertificateId the gift certificate id which tags will be found
     * @return the list of found tags
     */
    List<Tag> findByGiftCertificateId(long giftCertificateId);

    /**
     * Find tag by name in database.
     *
     * @param name the name of tag to find
     * @return the optional of found tag
     */
    Optional<Tag> findByName(String name);
}
