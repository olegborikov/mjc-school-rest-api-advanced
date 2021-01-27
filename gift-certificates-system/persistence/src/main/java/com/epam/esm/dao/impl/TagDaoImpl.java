package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Class {@code TagDaoImpl} is implementation of interface {@link TagDao} and intended to work with tag table.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Repository
public class TagDaoImpl implements TagDao {

    private static final String FIND_ALL = "SELECT t FROM Tag t";
    private static final String FIND_BY_NAME = "SELECT t FROM Tag t WHERE t.name = :name";
    private static final String REMOVE_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM gift_certificate_has_tag "
            + "WHERE tag_id_fk = :tag_id_fk";
    private static final String FIND_MOST_POPULAR_OF_USER = "SELECT t FROM GiftCertificate g INNER JOIN g.tags t "
            + "WHERE g.id IN (SELECT o.giftCertificateId FROM Order o "
            + "WHERE o.userId = :userId) GROUP BY t.id ORDER BY COUNT(t.id) DESC";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag add(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public List<Tag> findAll(Page page) {
        return entityManager.createQuery(FIND_ALL, Tag.class)
                .setFirstResult((page.getNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("Method update is unavailable for TagDaoImpl");
    }

    @Override
    public void remove(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
    }

    @Override
    public void removeGiftCertificateHasTag(long id) {
        entityManager.createNativeQuery(REMOVE_GIFT_CERTIFICATE_HAS_TAG)
                .setParameter("tag_id_fk", id)
                .executeUpdate();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return entityManager.createQuery(FIND_BY_NAME, Tag.class)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public Optional<Tag> findMostPopularOfUser(long userId) {
        return entityManager.createQuery(FIND_MOST_POPULAR_OF_USER, Tag.class)
                .setParameter("userId", userId)
                .setMaxResults(1)
                .getResultList().stream()
                .findFirst();
    }
}
