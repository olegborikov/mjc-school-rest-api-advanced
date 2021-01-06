package com.epam.esm.util;

public enum OrderType {
    ASC("ASC"),
    DESC("DESC");
    private final String sqlEquivalent;

    OrderType(String sqlEquivalent) {
        this.sqlEquivalent = sqlEquivalent;
    }

    public String getSqlEquivalent() {
        return sqlEquivalent;
    }
}
