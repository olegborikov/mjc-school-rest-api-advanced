package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ExceptionMessageKey;
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
        validateName(giftCertificate.getName());
        validateDescription(giftCertificate.getDescription());
        validatePrice(giftCertificate.getPrice());
        validateDuration(giftCertificate.getDuration());
    }

    /**
     * Validate gift certificate id.
     *
     * @param id the gift certificate id
     * @throws IncorrectParameterValueException an exception thrown in case incorrect id
     */
    public void validateId(long id) throws IncorrectParameterValueException {
        if (id < MIN_NUMBER) {
            throw new IncorrectParameterValueException(
                    ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_ID, String.valueOf(id));
        }
    }

    private void validateName(String name) throws IncorrectParameterValueException {
        if (StringUtils.isBlank(name) || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterValueException(
                    ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_NAME, name);
        }
    }

    private void validateDescription(String description) throws IncorrectParameterValueException {
        if (StringUtils.isBlank(description) || description.length() > MAX_LENGTH_DESCRIPTION) {
            throw new IncorrectParameterValueException(
                    ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_DESCRIPTION, description);
        }
    }

    private void validatePrice(BigDecimal price) throws IncorrectParameterValueException {
        if (price == null || price.scale() > MAX_SCALE
                || price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
            throw new IncorrectParameterValueException(
                    ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_PRICE, String.valueOf(price));
        }
    }

    private void validateDuration(int duration) throws IncorrectParameterValueException {
        if (duration < MIN_NUMBER || duration > MAX_DURATION) {
            throw new IncorrectParameterValueException(
                    ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_DURATION, String.valueOf(duration));
        }
    }
}
