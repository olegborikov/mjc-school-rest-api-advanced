package com.epam.esm.dao.impl;

import com.epam.esm.configuration.PersistenceConfiguration;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfiguration.class)
class TagDaoImplTest {

    private final TagDao tagDao;
    private static Tag tag1;
    private static Tag tag2;
    private static Tag tag3;
    private static Tag tag4;

    @BeforeAll
    static void setUp() {
        tag1 = Tag.builder()
                .name("Shop")
                .build();
        tag2 = Tag.builder()
                .name("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .build();
        tag3 = Tag.builder()
                .id(1L)
                .name("home")
                .build();
        tag4 = Tag.builder()
                .id(3L)
                .name("work")
                .build();
    }

    @AfterAll
    static void tearDown() {
        tag1 = null;
        tag2 = null;
        tag3 = null;
        tag4 = null;
    }

    @Autowired
    public TagDaoImplTest(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Test
    void addCorrectDataShouldReturnTagTest() {
        // when
        Tag actual = tagDao.add(tag1);

        // then
        assertNotNull(actual);
    }

    @Test
    void addCorrectDataShouldSetIdTest() {
        // given
        long expected = 5;

        // when
        tagDao.add(tag1);
        long actual = tag1.getId();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void addIncorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(DataIntegrityViolationException.class, () -> tagDao.add(tag2));
    }

    @Test
    void findAllShouldReturnListOfTagsTest() {
        // given
        long expected = 6;

        // when
        List<Tag> actual = tagDao.findAll();

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findByIdCorrectDataShouldReturnTagOptionalTest() {
        // given
        long id = 1;

        // when
        Optional<Tag> actual = tagDao.findById(id);

        // then
        assertEquals(Optional.of(tag3), actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnEmptyOptionalTest() {
        // given
        long id = 8;

        // when
        Optional<Tag> actual = tagDao.findById(id);

        // then
        assertFalse(actual.isPresent());
    }

    @Test
    void updateShouldThrowExceptionTest() {
        // given
        Tag tag = new Tag();

        // then
        assertThrows(UnsupportedOperationException.class, () -> tagDao.update(tag));
    }

    @Test
    void removeCorrectDataShouldNotThrowExceptionTest() {
        // given
        long id = 7;

        // then
        assertDoesNotThrow(() -> tagDao.remove(id));
    }

    @Test
    void removeGiftCertificateHasTagCorrectDataShouldNotThrowExceptionTest() {
        // given
        long id = 7;

        // then
        assertDoesNotThrow(() -> tagDao.removeGiftCertificateHasTag(id));
    }

    @Test
    void findByGiftCertificateIdCorrectDataShouldReturnListOfTagsTest() {
        // given
        long giftCertificateId = 2;
        int expected = 2;

        // when
        List<Tag> actual = tagDao.findByGiftCertificateId(giftCertificateId);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findByGiftCertificateIdCorrectDataShouldReturnEmptyListTest() {
        // given
        long giftCertificateId = 5;

        // when
        List<Tag> actual = tagDao.findByGiftCertificateId(giftCertificateId);

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    void findByNameCorrectDataShouldReturnTagOptionalTest() {
        // given
        String name = "work";

        // when
        Optional<Tag> actual = tagDao.findByName(name);

        // then
        assertEquals(Optional.of(tag4), actual);
    }

    @Test
    void findByNameCorrectDataShouldReturnEmptyOptionalTest() {
        // given
        String name = "Work";

        // when
        Optional<Tag> actual = tagDao.findByName(name);

        // then
        assertFalse(actual.isPresent());
    }
}
