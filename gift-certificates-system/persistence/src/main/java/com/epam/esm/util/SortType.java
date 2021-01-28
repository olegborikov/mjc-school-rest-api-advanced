package com.epam.esm.util;

/**
 * Enum {@code SortType} contains available types of sorting.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
public enum SortType {

    /**
     * Name sort type.
     */
    NAME("name"),
    /**
     * Create date sort type.
     */
    CREATE_DATE("createDate");

    private final String sortFieldName;

    SortType(String sortFieldName) {
        this.sortFieldName = sortFieldName;
    }

    /**
     * Gets sort field name corresponding to enum element.
     *
     * @return the sort field name
     */
    public String getSortFieldName() {
        return sortFieldName;
    }
}
