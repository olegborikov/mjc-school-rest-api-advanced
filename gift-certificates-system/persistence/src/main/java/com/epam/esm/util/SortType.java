package com.epam.esm.util;

public enum SortType {
    NAME("ORDER BY gift_certificate_name"),
    CREATE_DATE("ORDER BY create_date");
    private final String sqlEquivalent;

    SortType(String sqlEquivalent) {
        this.sqlEquivalent = sqlEquivalent;
    }

    public String getSqlEquivalent() {
        return sqlEquivalent;
    }
}
