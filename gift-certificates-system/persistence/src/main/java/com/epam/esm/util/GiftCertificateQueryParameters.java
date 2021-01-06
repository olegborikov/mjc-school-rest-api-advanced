package com.epam.esm.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateQueryParameters {
    private String tagName;
    private String name;
    private String description;
    private SortType sortType;
    private OrderType orderType;
}
