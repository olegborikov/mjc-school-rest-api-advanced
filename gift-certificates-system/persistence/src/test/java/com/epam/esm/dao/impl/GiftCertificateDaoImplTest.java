package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.util.OrderType;
import com.epam.esm.util.SortType;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GiftCertificateDaoImplTest {

    private GiftCertificateDao giftCertificateDao;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:script/gift_certificate_create.sql")
                .addScript("classpath:script/gift_certificate_fill_up.sql")
                .addScript("classpath:script/gift_certificate_has_tag_create.sql")
                .addScript("classpath:script/gift_certificate_has_tag_fill_up.sql")
                .addScript("classpath:script/tag_create.sql")
                .addScript("classpath:script/tag_fill_up.sql")
                .build();
        GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapper();
        giftCertificateDao = new GiftCertificateDaoImpl(new JdbcTemplate(dataSource), giftCertificateMapper);
    }

    @AfterEach
    void tearDown() {
        new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:script/gift_certificate_delete.sql")
                .addScript("classpath:script/gift_certificate_has_tag_delete.sql")
                .addScript("classpath:script/tag_delete.sql")
                .build();
        giftCertificateDao = null;
    }

    @Test
    void addCorrectDataShouldReturnGiftCertificate() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificate actual = giftCertificateDao.add(giftCertificate);
        assertNotNull(actual);
    }

    @Test
    void addCorrectDataShouldSetId() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        giftCertificateDao.add(giftCertificate);
        long actual = giftCertificate.getId();
        long expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void addIncorrectDataShouldThrowException() {
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < 21; i++) {
            name.append("aaaaa");
        }
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name(name.toString())
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.add(giftCertificate));
    }

    @Test
    void findAllShouldReturnListOfGiftCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        long actual = giftCertificates.size();
        long expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnGiftCertificateOptional() {
        long id = 1;
        GiftCertificate expected = GiftCertificate.builder()
                .id(1L)
                .name("Spa house")
                .description("Very good and not expensive")
                .price(new BigDecimal("100.1"))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 8, 2, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2021, 1, 2, 16, 0, 0))
                .build();
        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);
        assertEquals(Optional.of(expected), actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnEmptyOptional() {
        long id = 5;
        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);
        assertFalse(actual.isPresent());
    }

    @Test
    void updateCorrectDataShouldReturnGiftCertificate() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(4L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificate expected = GiftCertificate.builder()
                .id(4L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificate actual = giftCertificateDao.update(giftCertificate);
        assertEquals(expected, actual);
    }

    @Test
    void updateIncorrectDataShouldThrowException() {
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < 21; i++) {
            name.append("aaaaa");
        }
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(4L)
                .name(name.toString())
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.update(giftCertificate));
    }

    @Test
    void removeCorrectDataShouldNotThrowException() {
        long id = 1;
        assertDoesNotThrow(() -> giftCertificateDao.remove(id));
    }

    @Test
    void removeGiftCertificateHasTagCorrectDataShouldNotThrowException() {
        long id = 1;
        assertDoesNotThrow(() -> giftCertificateDao.removeGiftCertificateHasTag(id));
    }

    @Test
    void addGiftCertificateHasTagCorrectDataShouldNotThrowException() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(4L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(Arrays.asList(new Tag(1L, "Sport"), new Tag(2L, "Home")))
                .build();
        assertDoesNotThrow(() -> giftCertificateDao.addGiftCertificateHasTag(giftCertificate));
    }

    @Test
    void findByQueryParametersCorrectDataShouldReturnListOfGiftCertificates() {
        int expected = 2;
        GiftCertificateQueryParameters giftCertificateQueryParameters = GiftCertificateQueryParameters.builder()
                .tagName("school")
                .name("a")
                .description("i")
                .sortType(SortType.NAME)
                .orderType(OrderType.ASC)
                .build();
        List<GiftCertificate> actual = giftCertificateDao.findByQueryParameters(giftCertificateQueryParameters);
        assertEquals(expected, actual.size());
    }

    @Test
    void findByQueryParametersCorrectDataShouldReturnEmptyList() {
        GiftCertificateQueryParameters giftCertificateQueryParameters = GiftCertificateQueryParameters.builder()
                .tagName("School")
                .name("a")
                .description("i")
                .sortType(SortType.CREATE_DATE)
                .orderType(OrderType.DESC)
                .build();
        List<GiftCertificate> actual = giftCertificateDao.findByQueryParameters(giftCertificateQueryParameters);
        assertTrue(actual.isEmpty());
    }
}