package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import org.springframework.core.convert.converter.Converter;

public class StringToSortTypeConverter implements Converter<String, GiftCertificateQueryParametersDto.SortType> {

    @Override
    public GiftCertificateQueryParametersDto.SortType convert(String source) {
        return GiftCertificateQueryParametersDto.SortType.valueOf(source.toUpperCase());
    }
}
