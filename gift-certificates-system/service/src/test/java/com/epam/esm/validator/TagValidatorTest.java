package com.epam.esm.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagValidatorTest {
    @Test
    void isIdCorrectCorrectDataShouldReturnTrue() {
        String id = "5";
        boolean actual = TagValidator.isIdCorrect(id);
        assertTrue(actual);
    }

    @Test
    void isIdCorrectIncorrectDataShouldReturnFalse() {
        String id = "dsa";
        boolean actual = TagValidator.isIdCorrect(id);
        assertFalse(actual);
    }

    @Test
    void isNameCorrectCorrectDataShouldReturnTrue() {
        String name = "Travel";
        boolean actual = TagValidator.isNameCorrect(name);
        assertTrue(actual);
    }

    @Test
    void isNameCorrectIncorrectDataShouldReturnFalse() {
        StringBuilder name = new StringBuilder("");
        for (int i = 0; i < 21; i++) {
            name.append("aaaaa");
        }
        boolean actual = TagValidator.isNameCorrect(name.toString());
        assertFalse(actual);
    }
}
