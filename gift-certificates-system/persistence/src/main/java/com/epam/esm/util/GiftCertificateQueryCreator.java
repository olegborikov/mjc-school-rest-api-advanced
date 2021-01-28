package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import lombok.experimental.UtilityClass;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class {@code GiftCertificateQueryCreator} designed to create a criteria builder.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@UtilityClass
public class GiftCertificateQueryCreator {

    private final String TAGS = "tags";
    private final String NAME = "name";
    private final String PERCENT = "%";
    private final String DESCRIPTION = "description";

    /**
     * Create query from object of {@link GiftCertificateQueryParameters}.
     *
     * @param giftCertificateQueryParameters the gift certificate query parameters
     * @param criteriaBuilder                the criteria builder
     * @return the criteria query
     */
    public CriteriaQuery<GiftCertificate> createQuery(GiftCertificateQueryParameters giftCertificateQueryParameters,
                                                      CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
        List<Predicate> restrictions = new ArrayList<>();
        restrictions.addAll(addTagNames(giftCertificateQueryParameters, criteriaBuilder, giftCertificateRoot));
        restrictions.addAll(addName(giftCertificateQueryParameters, criteriaBuilder, giftCertificateRoot));
        restrictions.addAll(addDescription(giftCertificateQueryParameters, criteriaBuilder, giftCertificateRoot));
        criteriaQuery.select(giftCertificateRoot)
                .where(restrictions.toArray(new Predicate[]{}));
        addSortType(giftCertificateQueryParameters, criteriaBuilder, criteriaQuery, giftCertificateRoot);
        return criteriaQuery;
    }

    private List<Predicate> addTagNames(GiftCertificateQueryParameters giftCertificateQueryParameters,
                                        CriteriaBuilder criteriaBuilder, Root<GiftCertificate> giftCertificateRoot) {
        List<Predicate> restrictions = new ArrayList<>();
        if (giftCertificateQueryParameters.getTagNames() != null) {
            restrictions = Arrays.stream(giftCertificateQueryParameters.getTagNames())
                    .map(tagName -> criteriaBuilder.equal(giftCertificateRoot.join(TAGS).get(NAME), tagName))
                    .collect(Collectors.toList());
        }
        return restrictions;
    }

    private List<Predicate> addName(GiftCertificateQueryParameters giftCertificateQueryParameters,
                                    CriteriaBuilder criteriaBuilder, Root<GiftCertificate> giftCertificateRoot) {
        List<Predicate> restrictions = new ArrayList<>();
        if (giftCertificateQueryParameters.getName() != null) {
            restrictions.add(criteriaBuilder.like(giftCertificateRoot.get(NAME),
                    PERCENT + giftCertificateQueryParameters.getName() + PERCENT));
        }
        return restrictions;
    }

    private List<Predicate> addDescription(GiftCertificateQueryParameters giftCertificateQueryParameters,
                                           CriteriaBuilder criteriaBuilder, Root<GiftCertificate> giftCertificateRoot) {
        List<Predicate> restrictions = new ArrayList<>();
        if (giftCertificateQueryParameters.getDescription() != null) {
            restrictions.add(criteriaBuilder.like(giftCertificateRoot.get(DESCRIPTION),
                    PERCENT + giftCertificateQueryParameters.getDescription() + PERCENT));
        }
        return restrictions;
    }

    private void addSortType(GiftCertificateQueryParameters giftCertificateQueryParameters,
                             CriteriaBuilder criteriaBuilder, CriteriaQuery<GiftCertificate> criteriaQuery,
                             Root<GiftCertificate> giftCertificateRoot) {
        if (giftCertificateQueryParameters.getSortType() != null) {
            String sortFieldName = giftCertificateQueryParameters.getSortType().getSortFieldName();
            if (giftCertificateQueryParameters.getOrderType() != null
                    && giftCertificateQueryParameters.getOrderType().equals(OrderType.DESC)) {
                criteriaQuery.orderBy(criteriaBuilder.desc(giftCertificateRoot.get(sortFieldName)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.asc(giftCertificateRoot.get(sortFieldName)));
            }
        }
    }
}
