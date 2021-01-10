package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.IncorrectParameterValueException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Class {@code TagValidator} provides methods to validate fields of {@link com.epam.esm.entity.GiftCertificate}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@UtilityClass
public class GiftCertificateValidator {

    private final int MAX_SCALE = 2;
    private final BigDecimal MIN_PRICE = new BigDecimal("0.01");
    private final BigDecimal MAX_PRICE = new BigDecimal("999999.99");
    private final int MAX_LENGTH_NAME = 100;
    private final int MAX_LENGTH_DESCRIPTION = 1000;
    private final int MIN_NUMBER = 1;
    private final int MAX_DURATION = 1000;

    /**
     * Validate all fields of gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @throws IncorrectParameterValueException an exception thrown in case incorrect name,
     *                                          description, price or duration
     */
    public void validate(GiftCertificate giftCertificate) throws IncorrectParameterValueException {
        GiftCertificateValidator.validateName(giftCertificate.getName());
        GiftCertificateValidator.validateDescription(giftCertificate.getDescription());
        GiftCertificateValidator.validatePrice(giftCertificate.getPrice());
        GiftCertificateValidator.validateDuration(giftCertificate.getDuration());
    }

    /**
     * Validate gift certificate id.
     *
     * @param id the gift certificate id
     * @throws IncorrectParameterValueException an exception thrown in case incorrect id
     */
    public void validateId(Long id) throws IncorrectParameterValueException {
        if (id == null || id < MIN_NUMBER) {
            throw new IncorrectParameterValueException("Incorrect id value: " + id
                    + ". Id should be positive number.");
        }
    }

    private void validateName(String name) throws IncorrectParameterValueException {
        if (StringUtils.isBlank(name) || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterValueException("Incorrect name value: " + name
                    + ". Name should be string with length in range from 1 to 100 symbols.");
        }
    }

    private void validateDescription(String description) throws IncorrectParameterValueException {
        if (StringUtils.isBlank(description) || description.length() > MAX_LENGTH_DESCRIPTION) {
            throw new IncorrectParameterValueException("Incorrect description value: " + description
                    + ". Description should be string with length in range from 1 to 1000 symbols.");
        }
    }

    private void validatePrice(BigDecimal price) throws IncorrectParameterValueException {
        if (price == null || price.scale() > MAX_SCALE
                || price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
            throw new IncorrectParameterValueException("Incorrect price value: " + price
                    + ". Price should be positive number(< 1000000) and have two numbers in scale.");
        }
    }

    private void validateDuration(int duration) throws IncorrectParameterValueException {
        if (duration < MIN_NUMBER || duration > MAX_DURATION) {
            throw new IncorrectParameterValueException("Incorrect duration value: " + duration
                    + ". Duration should be positive number(< 1000).");
        }
    }
}
