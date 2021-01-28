package com.epam.esm.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class {@code GiftCertificateQueryParameters} consists parameters which are used to make selection to database.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateQueryParameters {

    private String[] tagNames;
    private String name;
    private String description;
    private SortType sortType;
    private OrderType orderType;
}
