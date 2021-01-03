package com.epam.esm.validator;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class TagValidator {
    private final int MIN_LENGTH_NAME = 1;
    private final int MAX_LENGTH_NAME = 100;

    public boolean isNameCorrect(String name) {
        return name != null && StringUtils.isNoneBlank(name)
                && name.length() >= MIN_LENGTH_NAME && name.length() <= MAX_LENGTH_NAME;
    }
}
