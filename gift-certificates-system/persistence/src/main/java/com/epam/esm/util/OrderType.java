package com.epam.esm.util;

public enum OrderType {
    ASC(" ASC"),
    DESC(" DESC");

    private final String sqlExpression;

    OrderType(String sqlExpression) {
        this.sqlExpression = sqlExpression;
    }

    public String getSqlExpression() {
        return sqlExpression;
    }
}
