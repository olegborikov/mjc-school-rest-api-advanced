package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    private static final String REMOVE_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM gift_certificate_has_tag "
            + "WHERE tag_id = :tag_id";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag add(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return entityManager.createQuery(FIND_ALL, Tag.class).getResultList();
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
                .setParameter("tag_id", id)
                .executeUpdate();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tag = criteriaQuery.from(Tag.class);
        criteriaQuery.select(tag).where(criteriaBuilder.equal(tag.get("name"), name));
        TypedQuery<Tag> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList().stream().findFirst();
    }
}
