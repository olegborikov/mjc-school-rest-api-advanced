package com.epam.esm.validator;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateValidatorTest {
    @Test
    void isNameCorrectCorrectDataShouldReturnTrue() {
        String name = "Quest";
        boolean actual = GiftCertificateValidator.isNameCorrect(name);
        assertTrue(actual);
    }

    @Test
    void isNameCorrectIncorrectDataShouldReturnFalse() {
        StringBuilder name = new StringBuilder("");
        for (int i = 0; i < 21; i++) {
            name.append("aaaaa");
        }
        boolean actual = GiftCertificateValidator.isNameCorrect(name.toString());
        assertFalse(actual);
    }

    @Test
    void isDescriptionCorrectCorrectDataShouldReturnTrue() {
        String description = "Very good and not expensive";
        boolean actual = GiftCertificateValidator.isDescriptionCorrect(description);
        assertTrue(actual);
    }

    @Test
    void isDescriptionCorrectIncorrectDataShouldReturnFalse() {
        StringBuilder description = new StringBuilder("");
        for (int i = 0; i < 210; i++) {
            description.append("aaaaa");
        }
        boolean actual = GiftCertificateValidator.isDescriptionCorrect(description.toString());
        assertFalse(actual);
    }

    @Test
    void isPriceCorrectCorrectDataShouldReturnTrue() {
        BigDecimal price = new BigDecimal("100.99");
        boolean actual = GiftCertificateValidator.isPriceCorrect(price);
        assertTrue(actual);
    }

    @Test
    void isPriceCorrectIncorrectDataShouldReturnFalse() {
        BigDecimal price = new BigDecimal("0");
        boolean actual = GiftCertificateValidator.isPriceCorrect(price);
        assertFalse(actual);
    }
}
