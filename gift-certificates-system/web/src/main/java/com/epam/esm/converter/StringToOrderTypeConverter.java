package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import org.springframework.core.convert.converter.Converter;

/**
 * Class {@code StringToOrderTypeConverter} is implementation of {@link Converter}
 * to convert {@code String} to {@code GiftCertificateQueryParametersDto.OrderType}.
 *
 * @author Oleg Borikov
 * @version 1.0
 * @see Converter
 */
public class StringToOrderTypeConverter implements Converter<String, GiftCertificateQueryParametersDto.OrderType> {

    /**
     * Convert the source object of type {@code String}
     * to target type {@code GiftCertificateQueryParametersDto.OrderType}.
     *
     * @param source the source object to convert which must be an instance of {@code String} (never {@code null})
     * @return the converted object which must be an instance of
     * {@code GiftCertificateQueryParametersDto.OrderType} (potentially {@code null})
     */
    @Override
    public GiftCertificateQueryParametersDto.OrderType convert(String source) {
        return GiftCertificateQueryParametersDto.OrderType.valueOf(source.toUpperCase());
    }
}
