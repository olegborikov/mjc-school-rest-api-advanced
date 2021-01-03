package com.epam.esm.validator;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@UtilityClass
public class GiftCertificateValidator {
    private final int MAX_SCALE = 2;
    private final BigDecimal MIN_PRICE = new BigDecimal("0");
    private final BigDecimal MAX_PRICE = new BigDecimal("100000000");
    private final int MIN_LENGTH = 1;
    private final int MAX_LENGTH_NAME = 100;
    private final int MAX_LENGTH_DESCRIPTION = 1000;

    public boolean isNameCorrect(String name) {
        return name != null && StringUtils.isNoneBlank(name)
                && name.length() >= MIN_LENGTH && name.length() <= MAX_LENGTH_NAME;
    }

    public boolean isDescriptionCorrect(String description) {
        return description != null && StringUtils.isNoneBlank(description)
                && description.length() >= MIN_LENGTH && description.length() <= MAX_LENGTH_DESCRIPTION;
    }

    public boolean isPriceCorrect(BigDecimal price) {
        return price.scale() <= MAX_SCALE
                && price.compareTo(MIN_PRICE) > 0 && price.compareTo(MAX_PRICE) < 0;
    }
}
