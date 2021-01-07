package com.epam.esm.validator;

import com.epam.esm.exception.IncorrectParameterValueException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class TagValidator {
    private final int MIN_LENGTH_NAME = 1;
    private final int MAX_LENGTH_NAME = 100;
    private final int MIN_ID = 1;

    public void validateId(Long id) {
        if (id == null || id < MIN_ID) {
            throw new IncorrectParameterValueException("Incorrect id value: " + id
                    + ". Id should be positive number.");
        }
    }

    public void validateName(String name) {
        if (name == null || StringUtils.isBlank(name)
                || name.length() > MAX_LENGTH_NAME || name.length() < MIN_LENGTH_NAME) {
            throw new IncorrectParameterValueException("Incorrect name value: " + name
                    + ". Name should be string with length in range from 1 to 100 symbols.");
        }
    }
}
