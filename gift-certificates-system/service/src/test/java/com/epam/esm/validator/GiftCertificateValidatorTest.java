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
    void validateCorrectDataShouldNotThrowException(GiftCertificate giftCertificate) {
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
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            name.append("aaaaa");
        }
        GiftCertificate giftCertificate5 = GiftCertificate.builder()
                .name(name.toString())
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
    void validateIncorrectDataShouldThrowException(GiftCertificate giftCertificate) {
        assertThrows(IncorrectParameterValueException.class,
                () -> GiftCertificateValidator.validate(giftCertificate));
    }

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
}
