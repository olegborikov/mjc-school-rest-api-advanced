package com.epam.esm.dao.impl;

import com.epam.esm.configuration.PersistenceConfiguration;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfiguration.class)
@Transactional
class TagDaoImplTest {

    private final TagDao tagDao;
    private static Tag tag1;
    private static Tag tag2;
    private static Tag tag3;
    private static Tag tag4;
    private static Tag tag5;
    private static Tag tag6;
    private static Page page1;
    private static Page page2;
    private static Page page3;

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
                .name("Sport")
                .build();
        tag5 = Tag.builder()
                .id(1L)
                .name("home")
                .build();
        tag6 = Tag.builder()
                .id(3L)
                .name("work")
                .build();
        page1 = Page.builder()
                .number(1)
                .size(5)
                .build();
        page2 = Page.builder()
                .number(3)
                .size(3)
                .build();
        page3 = Page.builder()
                .number(-3)
                .size(3)
                .build();
    }

    @AfterAll
    static void tearDown() {
        tag1 = null;
        tag2 = null;
        tag3 = null;
        tag4 = null;
        tag5 = null;
        tag6 = null;
        page1 = null;
        page2 = null;
        page3 = null;
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
        tagDao.add(tag4);

        // then
        assertEquals(expected, tag4.getId());
    }

    @Test
    void addCorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> tagDao.add(tag3));
    }

    @Test
    void addIncorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(DataIntegrityViolationException.class, () -> tagDao.add(tag2));
    }

    @Test
    void findAllCorrectDataShouldReturnListOfTagsTest() {
        // given
        long expected = 4;

        // when
        List<Tag> actual = tagDao.findAll(page1);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findAllCorrectDataShouldReturnEmptyListTest() {
        // when
        List<Tag> actual = tagDao.findAll(page2);

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    void findAllIncorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> tagDao.findAll(page3));
    }

    @Test
    void findByIdCorrectDataShouldReturnTagOptionalTest() {
        // given
        long id = 1;

        // when
        Optional<Tag> actual = tagDao.findById(id);

        // then
        assertEquals(Optional.of(tag5), actual);
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
        long id = 4;

        // then
        assertDoesNotThrow(() -> tagDao.remove(id));
    }

    @Test
    void removeIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = 10;

        // then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> tagDao.remove(id));
    }

    @Test
    void removeGiftCertificateHasTagCorrectDataShouldNotThrowExceptionTest() {
        // given
        long id = 4;

        // then
        assertDoesNotThrow(() -> tagDao.removeGiftCertificateHasTag(id));
    }

    @Test
    void findByNameCorrectDataShouldReturnTagOptionalTest() {
        // given
        String name = "work";

        // when
        Optional<Tag> actual = tagDao.findByName(name);

        // then
        assertEquals(Optional.of(tag6), actual);
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
