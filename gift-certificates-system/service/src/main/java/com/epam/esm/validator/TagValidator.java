package com.epam.esm.validator;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class TagValidator {
    private final int MIN_LENGTH_NAME = 1;
    private final int MAX_LENGTH_NAME = 100;
    private static final String ID_REGEX = "^[1-9]\\d{0,9}$";

    public static boolean isIdCorrect(String id) {
        return id != null && StringUtils.isNoneBlank(id) && id.matches(ID_REGEX);
    }

    public boolean isNameCorrect(String name) {
        return name != null && StringUtils.isNoneBlank(name)
                && name.length() >= MIN_LENGTH_NAME && name.length() <= MAX_LENGTH_NAME;
    }
}
