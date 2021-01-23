package com.epam.esm.util;

/**
 * Enum {@code SortType} contains available types of sorting and corresponding sql expressions.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
public enum SortType {

    /**
     * Name sort type.
     */
    NAME(" ORDER BY g.name"),
    /**
     * Create date sort type.
     */
    CREATE_DATE(" ORDER BY g.createDate");

    private final String sqlExpression;

    SortType(String sqlExpression) {
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
