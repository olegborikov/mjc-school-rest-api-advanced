package com.epam.esm.validator;

import com.epam.esm.exception.IncorrectParameterValueException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TagValidatorTest {

    public static Object[][] validateIdCorrectData() {
        return new Object[][]{
                {400},
                {1},
                {10000}
        };
    }

    @ParameterizedTest
    @MethodSource("validateIdCorrectData")
    void validateIdCorrectDataShouldNotThrowExceptionTest(long id) {
        assertDoesNotThrow(() -> TagValidator.validateId(id));
    }

    public static Object[][] validateIdIncorrectData() {
        return new Object[][]{
                {0},
                {-100}
        };
    }

    @ParameterizedTest
    @MethodSource("validateIdIncorrectData")
    void validateIdIncorrectDataShouldThrowExceptionTest(long id) {
        assertThrows(IncorrectParameterValueException.class, () -> TagValidator.validateId(id));
    }

    public static Object[][] validateNameCorrectData() {
        return new Object[][]{
                {"Sport"},
                {"T"},
                {"100"}
        };
    }

    @ParameterizedTest
    @MethodSource("validateNameCorrectData")
    void validateNameCorrectDataShouldNotThrowExceptionTest(String name) {
        assertDoesNotThrow(() -> TagValidator.validateName(name));
    }

    public static Object[][] validateNameIncorrectData() {
        return new Object[][]{
                {null},
                {"   "},
                {""},
                {"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"}
        };
    }

    @ParameterizedTest
    @MethodSource("validateNameIncorrectData")
    void validateNameIncorrectDataShouldThrowExceptionTest(String name) {
        assertThrows(IncorrectParameterValueException.class, () -> TagValidator.validateName(name));
    }
}
