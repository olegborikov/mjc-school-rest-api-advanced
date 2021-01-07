package com.epam.esm.validator;

import com.epam.esm.exception.IncorrectParameterValueException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TagValidatorTest {
    public static Object[][] validateIdCorrectData() {
        return new Object[][]{
                {400L},
                {1L},
                {10000L}
        };
    }

    @ParameterizedTest
    @MethodSource("validateIdCorrectData")
    void validateIdCorrectDataShouldNotThrowException(Long id) {
        assertDoesNotThrow(() -> TagValidator.validateId(id));
    }

    public static Object[][] validateIdIncorrectData() {
        return new Object[][]{
                {null},
                {0L},
                {-100L}
        };
    }

    @ParameterizedTest
    @MethodSource("validateIdIncorrectData")
    void validateIdIncorrectDataShouldThrowException(Long id) {
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
    void validateNameCorrectDataShouldNotThrowException(String name) {
        assertDoesNotThrow(() -> TagValidator.validateName(name));
    }

    public static Object[][] validateNameIncorrectData() {
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            name.append("aaaaa");
        }
        return new Object[][]{
                {null},
                {"   "},
                {""},
                {name.toString()}
        };
    }

    @ParameterizedTest
    @MethodSource("validateNameIncorrectData")
    void validateNameIncorrectDataShouldThrowException(String name) {
        assertThrows(IncorrectParameterValueException.class, () -> TagValidator.validateName(name));
    }
}
