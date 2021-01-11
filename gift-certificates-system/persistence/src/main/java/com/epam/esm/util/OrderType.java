package com.epam.esm.util;

/**
 * Enum {@code OrderType} contains available types of ordering and corresponding sql expressions.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
public enum OrderType {

    /**
     * Asc order type.
     */
    ASC(" ASC"),
    /**
     * Desc order type.
     */
    DESC(" DESC");

    private final String sqlExpression;

    OrderType(String sqlExpression) {
        this.sqlExpression = sqlExpression;
    }

    /**
     * Gets sql expression corresponding to enum element.
     *
     * @return the sql expression
     */
    public String getSqlExpression() {
        return sqlExpression;
    }
}
