package com.epam.esm.util;

public enum SortType {
    NAME(" ORDER BY gift_certificate_name"),
    CREATE_DATE(" ORDER BY create_date");

    private final String sqlExpression;

    SortType(String sqlExpression) {
        this.sqlExpression = sqlExpression;
    }

    public String getSqlExpression() {
        return sqlExpression;
    }
}
