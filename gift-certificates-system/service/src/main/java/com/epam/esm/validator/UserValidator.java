package com.epam.esm.validator;

import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.IncorrectParameterValueException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserValidator {

    private final int MIN_ID = 1;

    public void validateId(long id) throws IncorrectParameterValueException {
        if (id < MIN_ID) {
            throw new IncorrectParameterValueException(ExceptionMessageKey.INCORRECT_USER_ID, String.valueOf(id));
        }
    }
}
