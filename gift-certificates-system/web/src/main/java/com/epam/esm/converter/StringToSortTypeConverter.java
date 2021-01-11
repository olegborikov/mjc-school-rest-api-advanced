package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * Class {@code StringToSortTypeConverter} is implementation of {@link Converter}
 * to convert {@code String} to {@code GiftCertificateQueryParametersDto.SortType}.
 *
 * @author Oleg Borikov
 * @version 1.0
 * @see Converter
 */
@Slf4j
public class StringToSortTypeConverter implements Converter<String, GiftCertificateQueryParametersDto.SortType> {

    /**
     * Convert the source object of type {@code String} to target type {@code GiftCertificateQueryParametersDto.SortType}.
     *
     * @param source the source object to convert, which must be an instance of {@code String} (never {@code null})
     * @return the converted object, which must be an instance of
     * {@code GiftCertificateQueryParametersDto.SortType} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public GiftCertificateQueryParametersDto.SortType convert(String source) throws IllegalArgumentException {
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
