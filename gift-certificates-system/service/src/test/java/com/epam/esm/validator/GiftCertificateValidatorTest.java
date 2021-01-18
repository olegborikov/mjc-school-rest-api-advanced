package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.IncorrectParameterValueException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GiftCertificateValidatorTest {

    public static Object[][] validateCorrectData() {
        GiftCertificate giftCertificate1 = GiftCertificate.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal("0.01"))
                .duration(5)
                .build();
        GiftCertificate giftCertificate2 = GiftCertificate.builder()
                .name("Cinema")
                .description("123")
                .price(new BigDecimal("999999.99"))
                .duration(5)
                .build();
        GiftCertificate giftCertificate3 = GiftCertificate.builder()
                .name("Cinema")
                .description("Q")
                .price(new BigDecimal(100))
                .duration(1000)
                .build();
        GiftCertificate giftCertificate4 = GiftCertificate.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(1)
                .build();
        return new Object[][]{
                {giftCertificate1},
                {giftCertificate2},
                {giftCertificate3},
                {giftCertificate4}
        };
    }

    @ParameterizedTest
    @MethodSource("validateCorrectData")
    void validateCorrectDataShouldNotThrowExceptionTest(GiftCertificate giftCertificate) {
        assertDoesNotThrow(() -> GiftCertificateValidator.validate(giftCertificate));
    }

    public static Object[][] validateIncorrectData() {
        GiftCertificate giftCertificate1 = GiftCertificate.builder()
                .name("")
                .description("Best cinema in the city")
                .price(new BigDecimal("0.01"))
                .duration(5)
                .build();
        GiftCertificate giftCertificate2 = GiftCertificate.builder()
                .name("Cinema")
                .description(null)
                .price(new BigDecimal("999999.99"))
                .duration(5)
                .build();
        GiftCertificate giftCertificate3 = GiftCertificate.builder()
                .name("Cinema")
                .description("Q")
                .price(new BigDecimal(0))
                .duration(1000)
                .build();
        GiftCertificate giftCertificate4 = GiftCertificate.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal("1.231"))
                .duration(1)
                .build();
        GiftCertificate giftCertificate5 = GiftCertificate.builder()
                .name("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .description("Best cinema in the city")
                .price(new BigDecimal(1))
                .duration(1)
                .build();
        GiftCertificate giftCertificate6 = GiftCertificate.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(1))
                .duration(1001)
                .build();
        return new Object[][]{
                {giftCertificate1},
                {giftCertificate2},
                {giftCertificate3},
                {giftCertificate4},
                {giftCertificate5},
                {giftCertificate6}
        };
    }

    @ParameterizedTest
    @MethodSource("validateIncorrectData")
    void validateIncorrectDataShouldThrowExceptionTest(GiftCertificate giftCertificate) {
        assertThrows(IncorrectParameterValueException.class,
                () -> GiftCertificateValidator.validate(giftCertificate));
    }

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
        assertDoesNotThrow(() -> GiftCertificateValidator.validateId(id));
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
        assertThrows(IncorrectParameterValueException.class, () -> GiftCertificateValidator.validateId(id));
    }
}
