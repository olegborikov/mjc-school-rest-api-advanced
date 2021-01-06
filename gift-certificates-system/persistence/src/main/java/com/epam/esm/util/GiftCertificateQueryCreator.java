package com.epam.esm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GiftCertificateQueryCreator {
    private static final String SPACE = " ";
    private static final String WHERE = "WHERE";
    private static final String AND = "AND";
    private static final String TAG_NAME = "tag_name LIKE '";
    private static final String APOSTROPHE = "'";
    private static final String NAME = "gift_certificate_name LIKE '%";
    private static final String DESCRIPTION = "description LIKE '%";
    private static final String APOSTROPHE_PERCENT = "%'";
    private static final String GROUP_BY = "GROUP BY gift_certificate_id";

    public String createQuery(GiftCertificateQueryParameters giftCertificateQueryParameters) {
        StringBuilder condition = new StringBuilder();
        if (giftCertificateQueryParameters.getTagName() != null) {
            condition.append(SPACE);
            addOperator(condition);
            condition.append(SPACE);
            condition.append(TAG_NAME);
            condition.append(giftCertificateQueryParameters.getTagName());
            condition.append(APOSTROPHE);
        }
        if (giftCertificateQueryParameters.getName() != null) {
            condition.append(SPACE);
            addOperator(condition);
            condition.append(SPACE);
            condition.append(NAME);
            condition.append(giftCertificateQueryParameters.getName());
            condition.append(APOSTROPHE_PERCENT);
        }
        if (giftCertificateQueryParameters.getDescription() != null) {
            condition.append(SPACE);
            addOperator(condition);
            condition.append(SPACE);
            condition.append(DESCRIPTION);
            condition.append(giftCertificateQueryParameters.getDescription());
            condition.append(APOSTROPHE_PERCENT);
        }
        condition.append(SPACE);
        condition.append(GROUP_BY);
        if (giftCertificateQueryParameters.getSortType() != null) {
            condition.append(SPACE);
            condition.append(giftCertificateQueryParameters.getSortType().getSqlEquivalent());
            if (giftCertificateQueryParameters.getOrderType() != null) {
                condition.append(SPACE);
                condition.append(giftCertificateQueryParameters.getOrderType().getSqlEquivalent());
            }
        }
        return condition.toString();
    }

    private void addOperator(StringBuilder mainCondition) {
        if (mainCondition.length() == 1) {
            mainCondition.append(WHERE);
        } else {
            mainCondition.append(AND);
        }
    }
}
