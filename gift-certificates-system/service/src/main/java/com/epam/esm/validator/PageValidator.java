package com.epam.esm.validator;

import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.util.Page;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PageValidator {

    private final int MIN_NUMERIC = 1;
    private final int MAX_SIZE = 100;

    public void validate(Page page) throws IncorrectParameterValueException {
        validateNumber(page.getNumber());
        validateSize(page.getSize());
    }

    private void validateNumber(int number) throws IncorrectParameterValueException {
        if (number < MIN_NUMERIC) {
            throw new IncorrectParameterValueException(
                    ExceptionMessageKey.INCORRECT_PAGE_NUMBER, String.valueOf(number));
        }
    }

    private void validateSize(int size) throws IncorrectParameterValueException {
        if (size < MIN_NUMERIC || size > MAX_SIZE) {
            throw new IncorrectParameterValueException(
                    ExceptionMessageKey.INCORRECT_PAGE_SIZE, String.valueOf(size));
        }
    }
}
