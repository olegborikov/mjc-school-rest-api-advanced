package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TagDaoImplTest {

    private TagDao tagDao;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:script/tag_create.sql")
                .addScript("classpath:script/tag_fill_up.sql")
                .addScript("classpath:script/gift_certificate_has_tag_create.sql")
                .addScript("classpath:script/gift_certificate_has_tag_fill_up.sql")
                .build();
        TagMapper tagMapper = new TagMapper();
        tagDao = new TagDaoImpl(new JdbcTemplate(dataSource), tagMapper);
    }

    @AfterEach
    void tearDown() {
        new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:script/tag_delete.sql")
                .addScript("classpath:script/gift_certificate_has_tag_delete.sql")
                .build();
        tagDao = null;
    }

    @Test
    void addCorrectDataShouldReturnTag() {
        // given
        Tag tag = Tag.builder()
                .name("Shop")
                .build();

        // when
        Tag actual = tagDao.add(tag);

        // then
        assertNotNull(actual);
    }

    @Test
    void addCorrectDataShouldSetId() {
        // given
        Tag tag = Tag.builder()
                .name("Shop")
                .build();
        long expected = 5;

        // when
        tagDao.add(tag);
        long actual = tag.getId();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void addIncorrectDataShouldThrowException() {
        // given
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < 21; i++) {
            name.append("aaaaa");
        }
        Tag tag = Tag.builder()
                .name(name.toString())
                .build();

        // then
        assertThrows(DataIntegrityViolationException.class, () -> tagDao.add(tag));
    }

    @Test
    void findAllShouldReturnListOfTags() {
        // given
        List<Tag> tags = tagDao.findAll();
        long expected = 4;

        // when
        long actual = tags.size();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnTagOptional() {
        // given
        long id = 1;
        Tag expected = Tag.builder()
                .id(1L)
                .name("home")
                .build();

        // when
        Optional<Tag> actual = tagDao.findById(id);

        // then
        assertEquals(Optional.of(expected), actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnEmptyOptional() {
        // given
        long id = 5;

        // when
        Optional<Tag> actual = tagDao.findById(id);

        // then
        assertFalse(actual.isPresent());
    }

    @Test
    void updateShouldThrowException() {
        // given
        Tag tag = new Tag();

        // then
        assertThrows(UnsupportedOperationException.class, () -> tagDao.update(tag));
    }

    @Test
    void removeCorrectDataShouldNotThrowException() {
        // given
        long id = 1;

        // then
        assertDoesNotThrow(() -> tagDao.remove(id));
    }

    @Test
    void removeGiftCertificateHasTagCorrectDataShouldNotThrowException() {
        // given
        long id = 1;

        // then
        assertDoesNotThrow(() -> tagDao.removeGiftCertificateHasTag(id));
    }

    @Test
    void findByGiftCertificateIdCorrectDataShouldReturnListOfTags() {
        // given
        long giftCertificateId = 1;
        int expected = 2;

        // when
        List<Tag> actual = tagDao.findByGiftCertificateId(giftCertificateId);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findByGiftCertificateIdCorrectDataShouldReturnEmptyList() {
        // given
        long giftCertificateId = 5;

        // when
        List<Tag> actual = tagDao.findByGiftCertificateId(giftCertificateId);

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    void findByNameCorrectDataShouldReturnTagOptional() {
        // given
        String name = "work";
        Tag expected = Tag.builder()
                .id(3L)
                .name("work")
                .build();

        // when
        Optional<Tag> actual = tagDao.findByName(name);

        // then
        assertEquals(Optional.of(expected), actual);
    }

    @Test
    void findByNameCorrectDataShouldReturnEmptyOptional() {
        // given
        String name = "Work";

        // when
        Optional<Tag> actual = tagDao.findByName(name);

        // then
        assertFalse(actual.isPresent());
    }
}
