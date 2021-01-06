package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToOrderTypeConverter implements Converter<String, GiftCertificateQueryParametersDto.OrderType> {
    @Override
    public GiftCertificateQueryParametersDto.OrderType convert(String source) {
        GiftCertificateQueryParametersDto.OrderType orderType = null;
        String sourceUpperCase = source.toUpperCase();
        try {
            orderType = GiftCertificateQueryParametersDto.OrderType.valueOf(sourceUpperCase);
        } catch (IllegalArgumentException e) {
            log.error("Incorrect OrderType enum element: " + sourceUpperCase);
        }
        return orderType;
    }
}
