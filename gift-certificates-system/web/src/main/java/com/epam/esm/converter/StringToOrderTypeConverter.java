package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * Class {@code StringToOrderTypeConverter} is implementation of {@link Converter}
 * to convert {@code String} to {@code GiftCertificateQueryParametersDto.OrderType}.
 *
 * @author Oleg Borikov
 * @version 1.0
 * @see Converter
 */
@Slf4j
public class StringToOrderTypeConverter implements Converter<String, GiftCertificateQueryParametersDto.OrderType> {

    /**
     * Convert the source object of type {@code String} to target type {@code GiftCertificateQueryParametersDto.OrderType}.
     *
     * @param source the source object to convert, which must be an instance of {@code String} (never {@code null})
     * @return the converted object, which must be an instance of
     * {@code GiftCertificateQueryParametersDto.OrderType} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public GiftCertificateQueryParametersDto.OrderType convert(String source) throws IllegalArgumentException {
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
