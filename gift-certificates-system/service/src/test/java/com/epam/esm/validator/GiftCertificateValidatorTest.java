package com.epam.esm.validator;

import com.epam.esm.exception.IncorrectParameterValueException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GiftCertificateValidatorTest {
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
        assertDoesNotThrow(() -> GiftCertificateValidator.validateId(id));
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
        assertThrows(IncorrectParameterValueException.class, () -> GiftCertificateValidator.validateId(id));
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
        assertDoesNotThrow(() -> GiftCertificateValidator.validateName(name));
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
        assertThrows(IncorrectParameterValueException.class, () -> GiftCertificateValidator.validateName(name));
    }

    public static Object[][] validateDescriptionCorrectData() {
        return new Object[][]{
                {"Sport"},
                {"T"},
                {"100"}
        };
    }

    @ParameterizedTest
    @MethodSource("validateDescriptionCorrectData")
    void validateDescriptionCorrectDataShouldNotThrowException(String description) {
        assertDoesNotThrow(() -> GiftCertificateValidator.validateDescription(description));
    }

    public static Object[][] validateDescriptionIncorrectData() {
        StringBuilder description = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            description.append("aaaaa");
        }
        return new Object[][]{
                {null},
                {"   "},
                {""},
                {description.toString()}
        };
    }

    @ParameterizedTest
    @MethodSource("validateDescriptionIncorrectData")
    void validateDescriptionIncorrectDataShouldThrowException(String description) {
        assertThrows(IncorrectParameterValueException.class, () ->
                GiftCertificateValidator.validateDescription(description));
    }

    public static Object[][] validatePriceCorrectData() {
        return new Object[][]{
                {new BigDecimal("100.12")},
                {new BigDecimal("0.01")},
                {new BigDecimal("10")},
                {new BigDecimal("999999.99")}
        };
    }

    @ParameterizedTest
    @MethodSource("validatePriceCorrectData")
    void validatePriceCorrectDataShouldNotThrowException(BigDecimal price) {
        assertDoesNotThrow(() -> GiftCertificateValidator.validatePrice(price));
    }

    public static Object[][] validatePriceIncorrectData() {
        return new Object[][]{
                {null},
                {new BigDecimal("100.123")},
                {new BigDecimal("0")},
                {new BigDecimal("-100")},
                {new BigDecimal("1000000")}
        };
    }

    @ParameterizedTest
    @MethodSource("validatePriceIncorrectData")
    void validatePriceIncorrectDataShouldThrowException(BigDecimal price) {
        assertThrows(IncorrectParameterValueException.class, () ->
                GiftCertificateValidator.validatePrice(price));
    }

    public static Object[][] validateDurationCorrectData() {
        return new Object[][]{
                {1},
                {100},
                {1000}
        };
    }

    @ParameterizedTest
    @MethodSource("validateDurationCorrectData")
    void validateDurationCorrectDataShouldNotThrowException(int duration) {
        assertDoesNotThrow(() -> GiftCertificateValidator.validateDuration(duration));
    }

    public static Object[][] validateDurationIncorrectData() {
        return new Object[][]{
                {0},
                {-100},
                {1001}
        };
    }

    @ParameterizedTest
    @MethodSource("validateDurationIncorrectData")
    void validateDurationIncorrectDataShouldThrowException(int duration) {
        assertThrows(IncorrectParameterValueException.class, () ->
                GiftCertificateValidator.validateDuration(duration));
    }

    public static Object[][] validateDatesCorrectData() {
        return new Object[][]{
                {LocalDateTime.of(2020, 7, 17, 15, 33, 10),
                        LocalDateTime.of(2020, 7, 17, 15, 33, 10)},
                {LocalDateTime.of(2019, 7, 17, 15, 33, 10),
                        LocalDateTime.of(2020, 7, 17, 15, 33, 10)},
                {LocalDateTime.of(2017, 7, 17, 15, 33, 10),
                        LocalDateTime.of(2018, 7, 17, 15, 33, 10)},
        };
    }

    @ParameterizedTest
    @MethodSource("validateDatesCorrectData")
    void validateDatesCorrectDataShouldNotThrowException(LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        assertDoesNotThrow(() -> GiftCertificateValidator.validateDates(createDate, lastUpdateDate));
    }

    public static Object[][] validateDatesIncorrectData() {
        return new Object[][]{
                {null, LocalDateTime.of(2020, 7, 17, 15, 33, 10)},
                {LocalDateTime.of(2020, 7, 17, 15, 33, 10), null},
                {LocalDateTime.of(2020, 7, 17, 15, 33, 10),
                        LocalDateTime.of(2019, 7, 17, 15, 33, 10)},
                {LocalDateTime.of(2020, 7, 17, 15, 33, 11),
                        LocalDateTime.of(2019, 7, 17, 15, 33, 10)}
        };
    }

    @ParameterizedTest
    @MethodSource("validateDatesIncorrectData")
    void validateDatesIncorrectDataShouldThrowException(LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        assertThrows(IncorrectParameterValueException.class, () ->
                GiftCertificateValidator.validateDates(createDate, lastUpdateDate));
    }
}
