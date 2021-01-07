package com.epam.esm.validator;

import com.epam.esm.exception.IncorrectParameterValueException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@UtilityClass
public class GiftCertificateValidator {
    private final int MAX_SCALE = 2;
    private final BigDecimal MIN_PRICE = new BigDecimal("0.01");
    private final BigDecimal MAX_PRICE = new BigDecimal("999999.99");
    private final int MIN_LENGTH = 1;
    private final int MAX_LENGTH_NAME = 100;
    private final int MAX_LENGTH_DESCRIPTION = 1000;
    private final int MIN_NUMBER = 1;
    private final int MAX_DURATION = 1000;

    public void validateId(Long id) {
        if (id == null || id < MIN_NUMBER) {
            throw new IncorrectParameterValueException("Incorrect id value: " + id
                    + ". Id should be positive number.");
        }
    }

    public void validateName(String name) {
        if (name == null || StringUtils.isBlank(name)
                || name.length() < MIN_LENGTH || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterValueException("Incorrect name value: " + name
                    + ". Name should be string with length in range from 1 to 100 symbols.");
        }
    }

    public void validateDescription(String description) {
        if (description == null || StringUtils.isBlank(description)
                || description.length() < MIN_LENGTH || description.length() > MAX_LENGTH_DESCRIPTION) {
            throw new IncorrectParameterValueException("Incorrect description value: " + description
                    + ". Description should be string with length in range from 1 to 1000 symbols.");
        }
    }

    public void validatePrice(BigDecimal price) {
        if (price == null || price.scale() > MAX_SCALE
                || price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
            throw new IncorrectParameterValueException("Incorrect price value: " + price
                    + ". Price should be positive number(< 1000000) and have two numbers in scale.");
        }
    }

    public void validateDuration(int duration) {
        if (duration < MIN_NUMBER || duration > MAX_DURATION) {
            throw new IncorrectParameterValueException("Incorrect duration value: " + duration
                    + ". Duration should be positive number(< 1000).");
        }
    }

    public void validateDates(LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        if (createDate == null || lastUpdateDate == null || lastUpdateDate.isBefore(createDate)) {
            throw new IncorrectParameterValueException("Incorrect dates values createDate: " + createDate
                    + ", lastUpdateDate: " + lastUpdateDate + ". Create date should be before last update date.");
        }
    }
}
