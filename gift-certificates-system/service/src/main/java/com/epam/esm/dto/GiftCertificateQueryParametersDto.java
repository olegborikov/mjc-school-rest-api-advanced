package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class {@code GiftCertificateQueryParametersDto} is implementation of pattern DTO
 * for transmission {@link com.epam.esm.util.GiftCertificateQueryParameters} between service and controller.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateQueryParametersDto {

    private String tagName;
    private String name;
    private String description;
    private SortType sortType;
    private OrderType orderType;

    /**
     * Enum {@code SortType} contains available types of sorting.
     */
    public enum SortType {

        /**
         * Name sort type.
         */
        NAME,
        /**
         * Create date sort type.
         */
        CREATE_DATE
    }

    /**
     * Enum {@code OrderType} contains available types of ordering.
     */
    public enum OrderType {

        /**
         * Asc order type.
         */
        ASC,
        /**
         * Desc order type.
         */
        DESC
    }
}
