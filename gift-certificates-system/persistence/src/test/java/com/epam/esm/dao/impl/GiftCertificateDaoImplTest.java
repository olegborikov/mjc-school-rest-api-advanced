package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateDaoImplTest {
    private GiftCertificateDao giftCertificateDao;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:script/gift_certificate_create.sql")
                .addScript("classpath:script/gift_certificate_fill_up.sql")
                .build();
        giftCertificateDao = new GiftCertificateDaoImpl();
        giftCertificateDao.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @AfterEach
    void tearDown() {
        new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:script/gift_certificate_delete.sql")
                .build();
        giftCertificateDao = null;
    }

    @Test
    void addCorrectDataShouldReturnTrue() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        boolean actual = giftCertificateDao.add(giftCertificate);
        assertTrue(actual);
    }

    @Test
    void addCorrectDataShouldSetId() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        giftCertificateDao.add(giftCertificate);
        long actual = giftCertificate.getId();
        long expected = 5;
        assertEquals(actual, expected);
    }

    @Test
    void addIncorrectDataShouldThrowException() {
        StringBuilder name = new StringBuilder("");
        for (int i = 0; i < 21; i++) {
            name.append("aaaaa");
        }
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name(name.toString())
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> {
            giftCertificateDao.add(giftCertificate);
        });
    }

    @Test
    void findAllShouldReturnListOfGiftCertificates() {
        List<GiftCertificate> tags = giftCertificateDao.findAll();
        long actual = tags.size();
        long expected = 4;
        assertEquals(actual, expected);
    }

    @Test
    void updateCorrectDataShouldReturnTrue() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(4L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        boolean actual = giftCertificateDao.update(giftCertificate);
        assertTrue(actual);
    }

    @Test
    void updateIncorrectDataShouldReturnFalse() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(-1L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        boolean actual = giftCertificateDao.update(giftCertificate);
        assertFalse(actual);
    }

    @Test
    void updateIncorrectDataShouldThrowException() {
        StringBuilder name = new StringBuilder("");
        for (int i = 0; i < 21; i++) {
            name.append("aaaaa");
        }
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(4L)
                .name(name.toString())
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> {
            giftCertificateDao.update(giftCertificate);
        });
    }

    @Test
    void removeCorrectDataShouldReturnTrue() {
        long id = 1;
        boolean actual = giftCertificateDao.remove(id);
        assertTrue(actual);
    }

    @Test
    void removeIncorrectDataShouldReturnFalse() {
        long id = -1;
        boolean actual = giftCertificateDao.remove(id);
        assertFalse(actual);
    }
}
