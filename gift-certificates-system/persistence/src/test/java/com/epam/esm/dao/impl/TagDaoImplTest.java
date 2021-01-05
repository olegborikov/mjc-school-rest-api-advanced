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

import static org.junit.jupiter.api.Assertions.*;

class TagDaoImplTest {
    private TagDao tagDao;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:script/tag_create.sql")
                .addScript("classpath:script/tag_fill_up.sql")
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
                .build();
        tagDao = null;
    }

    @Test
    void addCorrectDataShouldReturnTag() {
        Tag tag = Tag.builder()
                .name("Shop")
                .build();
        Tag actual = tagDao.add(tag);
        assertNotNull(actual);
    }

    @Test
    void addCorrectDataShouldSetId() {
        Tag tag = Tag.builder()
                .name("Shop")
                .build();
        tagDao.add(tag);
        long actual = tag.getId();
        long expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void addIncorrectDataShouldThrowException() {
        StringBuilder name = new StringBuilder("");
        for (int i = 0; i < 21; i++) {
            name.append("aaaaa");
        }
        Tag tag = Tag.builder()
                .name(name.toString())
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> {
            tagDao.add(tag);
        });
    }

    @Test
    void findAllShouldReturnListOfTags() {
        List<Tag> tags = tagDao.findAll();
        long actual = tags.size();
        long expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnTagOptional() {
        long id = 1;
        Tag expected = Tag.builder()
                .id(1L)
                .name("home")
                .build();
        Optional<Tag> actual = tagDao.findById(id);
        assertEquals(actual, Optional.of(expected));
    }

    @Test
    void findByIdIncorrectDataShouldReturnEmptyOptional() {
        long id = -1;
        Optional<Tag> actual = tagDao.findById(id);
        assertFalse(actual.isPresent());
    }

    @Test
    void updateRandomDataShouldThrowException() {
        Tag tag = new Tag();
        assertThrows(UnsupportedOperationException.class, () -> {
            tagDao.update(tag);
        });
    }

    @Test
    void removeCorrectDataShouldReturnTrue() {
        long id = 1;
        boolean actual = tagDao.remove(id);
        assertTrue(actual);
    }

    @Test
    void removeIncorrectDataShouldReturnFalse() {
        long id = -1;
        boolean actual = tagDao.remove(id);
        assertFalse(actual);
    }
}
