package com.epam.esm.util;

import lombok.experimental.UtilityClass;

import java.text.MessageFormat;

/**
 * Class {@code GiftCertificateQueryCreator} designed to create a selection condition.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@UtilityClass
public class GiftCertificateQueryCreator {

    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String TAG_NAME = "t.name LIKE '%s'";
    private static final String NAME = "g.name LIKE ''%{0}%''";
    private static final String DESCRIPTION = "g.description LIKE ''%{0}%''";
    private static final String GROUP_BY = " GROUP BY g.id";

    /**
     * Create query from object of {@link GiftCertificateQueryParameters}.
     *
     * @param giftCertificateQueryParameters the gift certificate query parameters
     * @return the selection condition
     */
    public String createQuery(GiftCertificateQueryParameters giftCertificateQueryParameters) {
        StringBuilder condition = new StringBuilder();
        addTagName(giftCertificateQueryParameters, condition);
        addName(giftCertificateQueryParameters, condition);
        addDescription(giftCertificateQueryParameters, condition);
        condition.append(GROUP_BY);
        addSortType(giftCertificateQueryParameters, condition);
        return condition.toString();
    }

    private void addTagName(GiftCertificateQueryParameters giftCertificateQueryParameters, StringBuilder condition) {
        if (giftCertificateQueryParameters.getTagName() != null) {
            addOperator(condition);
            condition.append(String.format(TAG_NAME, giftCertificateQueryParameters.getTagName()));
        }
    }

    private void addName(GiftCertificateQueryParameters giftCertificateQueryParameters, StringBuilder condition) {
        if (giftCertificateQueryParameters.getName() != null) {
            addOperator(condition);
            condition.append(MessageFormat.format(NAME, giftCertificateQueryParameters.getName()));
        }
    }

    private void addDescription(GiftCertificateQueryParameters giftCertificateQueryParameters,
                                StringBuilder condition) {
        if (giftCertificateQueryParameters.getDescription() != null) {
            addOperator(condition);
            condition.append(MessageFormat.format(DESCRIPTION, giftCertificateQueryParameters.getDescription()));
        }
    }

    private void addSortType(GiftCertificateQueryParameters giftCertificateQueryParameters, StringBuilder condition) {
        if (giftCertificateQueryParameters.getSortType() != null) {
            condition.append(giftCertificateQueryParameters.getSortType().getSqlExpression());
            if (giftCertificateQueryParameters.getOrderType() != null) {
                condition.append(giftCertificateQueryParameters.getOrderType().getSqlExpression());
            }
        }
    }

    private void addOperator(StringBuilder condition) {
        if (condition.toString().isEmpty()) {
            condition.append(WHERE);
        } else {
            condition.append(AND);
        }
    }
}
