package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQueryCreator;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.util.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Class {@code GiftCertificateDaoImpl} is implementation of interface
 * {@link GiftCertificateDao} and intended to work with gift_certificate table.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String FIND_ALL = "SELECT g FROM GiftCertificate g";
    private static final String REMOVE_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM gift_certificate_has_tag "
            + "WHERE gift_certificate_id = :gift_certificate_id";
    private static final String FIND_BY_QUERY_PARAMETERS = "SELECT g FROM GiftCertificate g LEFT JOIN "
            + "g.tags t";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll(Page page) {
        return entityManager.createQuery(FIND_ALL, GiftCertificate.class)
                .setFirstResult((page.getNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public void remove(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        entityManager.remove(giftCertificate);
    }

    @Override
    public void removeGiftCertificateHasTag(long id) {
        entityManager.createNativeQuery(REMOVE_GIFT_CERTIFICATE_HAS_TAG)
                .setParameter("gift_certificate_id", id)
                .executeUpdate();
    }

    @Override
    public List<GiftCertificate> findByQueryParameters(
            GiftCertificateQueryParameters giftCertificateQueryParameters, Page page) {
        String condition = GiftCertificateQueryCreator.createQuery(giftCertificateQueryParameters);
        return entityManager.createQuery(FIND_BY_QUERY_PARAMETERS + condition, GiftCertificate.class)
                .setFirstResult((page.getNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }
}
