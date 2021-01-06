package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public enum SortType {
        NAME,
        CREATE_DATE
    }

    public enum OrderType {
        ASC,
        DESC
    }
}
