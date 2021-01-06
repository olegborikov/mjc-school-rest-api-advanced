package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToSortTypeConverter implements Converter<String, GiftCertificateQueryParametersDto.SortType> {
    @Override
    public GiftCertificateQueryParametersDto.SortType convert(String source) {
        GiftCertificateQueryParametersDto.SortType sortType = null;
        String sourceUpperCase = source.toUpperCase();
        try {
            sortType = GiftCertificateQueryParametersDto.SortType.valueOf(sourceUpperCase);
        } catch (IllegalArgumentException e) {
            log.error("Incorrect SortType enum element: " + sourceUpperCase);
        }
        return sortType;
    }
}
