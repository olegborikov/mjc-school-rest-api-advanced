package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQueryParameters;

import java.util.List;

/**
 * Interface {@code GiftCertificateDao} describes abstract behavior
 * for working with gift_certificate table in database.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
public interface GiftCertificateDao extends CrudDao<GiftCertificate> {

    /**
     * Add gift certificate to gift_certificate_has_tag cross table.
     *
     * @param giftCertificate the gift certificate which gift certificate id
     *                        and tags id will be added to cross table
     */
    void addGiftCertificateHasTag(GiftCertificate giftCertificate);

    /**
     * Find gift certificates by query parameters in database.
     *
     * @param giftCertificateQueryParameters the gift certificate query parameters
     * @return the list of found gift certificates
     */
    List<GiftCertificate> findByQueryParameters(GiftCertificateQueryParameters giftCertificateQueryParameters);

    /**
     * Remove gift certificates from gift_certificate_has_tag cross table.
     *
     * @param id the id of gift certificates to remove from cross table
     */
    void removeGiftCertificateHasTag(long id);
}
