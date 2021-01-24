package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import org.springframework.core.convert.converter.Converter;

public class StringToOrderTypeConverter implements Converter<String, GiftCertificateQueryParametersDto.OrderType> {

    @Override
    public GiftCertificateQueryParametersDto.OrderType convert(String source) {
        return GiftCertificateQueryParametersDto.OrderType.valueOf(source.toUpperCase());
    }
}
