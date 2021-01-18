package com.epam.esm.validator;

import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.IncorrectParameterValueException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * Class {@code TagValidator} provides methods to validate fields of {@link com.epam.esm.entity.Tag}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@UtilityClass
public class TagValidator {

    private final int MAX_LENGTH_NAME = 100;
    private final int MIN_ID = 1;

    /**
     * Validate tag id.
     *
     * @param id the tag id
     * @throws IncorrectParameterValueException an exception thrown in case incorrect id
     */
    public void validateId(long id) throws IncorrectParameterValueException {
        if (id < MIN_ID) {
            throw new IncorrectParameterValueException(ExceptionMessageKey.INCORRECT_TAG_ID, String.valueOf(id));
        }
    }

    /**
     * Validate tag name.
     *
     * @param name the tag name
     * @throws IncorrectParameterValueException an exception thrown in case incorrect name
     */
    public void validateName(String name) throws IncorrectParameterValueException {
        if (StringUtils.isBlank(name) || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterValueException(ExceptionMessageKey.INCORRECT_TAG_NAME, name);
        }
    }
}
