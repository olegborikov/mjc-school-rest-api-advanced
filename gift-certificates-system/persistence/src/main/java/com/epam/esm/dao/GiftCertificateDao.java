package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.util.Page;

import java.util.List;

/**
 * Interface {@code GiftCertificateDao} describes abstract behavior
 * for working with gift_certificate table in database.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
public interface GiftCertificateDao extends CrudDao<GiftCertificate> {

    List<GiftCertificate> findByQueryParameters(
            GiftCertificateQueryParameters giftCertificateQueryParameters, Page page);

    /**
     * Remove gift certificates from gift_certificate_has_tag cross table.
     *
     * @param id the id of gift certificates to remove from cross table
     */
    void removeGiftCertificateHasTag(long id);
}
