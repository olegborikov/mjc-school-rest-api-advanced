package com.epam.esm.dao.impl;

import com.epam.esm.configuration.TestPersistenceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.util.OrderType;
import com.epam.esm.util.SortType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPersistenceConfiguration.class)
@ActiveProfiles("dev")
class GiftCertificateDaoImplTest {

    private final GiftCertificateDao giftCertificateDao;
    private static GiftCertificate giftCertificate1;
    private static GiftCertificate giftCertificate2;
    private static GiftCertificate giftCertificate3;
    private static GiftCertificate giftCertificate4;
    private static GiftCertificate giftCertificate5;
    private static GiftCertificateQueryParameters giftCertificateQueryParameters1;
    private static GiftCertificateQueryParameters giftCertificateQueryParameters2;

    @BeforeAll
    static void setUp() {
        giftCertificate1 = GiftCertificate.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        giftCertificate2 = GiftCertificate.builder()
                .name("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        giftCertificate3 = GiftCertificate.builder()
                .id(1L)
                .name("Spa house")
                .description("Very good and not expensive")
                .price(new BigDecimal("100.1"))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 8, 2, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2021, 1, 2, 16, 0, 0))
                .build();
        giftCertificate4 = GiftCertificate.builder()
                .id(1L)
                .name("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        giftCertificate5 = GiftCertificate.builder()
                .id(4L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(Arrays.asList(new Tag(1L, "Sport"), new Tag(2L, "Home")))
                .build();
        giftCertificateQueryParameters1 = GiftCertificateQueryParameters.builder()
                .tagName("school")
                .name("a")
                .description("i")
                .sortType(SortType.NAME)
                .orderType(OrderType.ASC)
                .build();
        giftCertificateQueryParameters2 = GiftCertificateQueryParameters.builder()
                .tagName("School")
                .name("a")
                .description("i")
                .sortType(SortType.CREATE_DATE)
                .orderType(OrderType.DESC)
                .build();
    }

    @AfterAll
    static void tearDown() {
        giftCertificate1 = null;
        giftCertificate2 = null;
        giftCertificate3 = null;
        giftCertificate4 = null;
        giftCertificate5 = null;
        giftCertificateQueryParameters1 = null;
        giftCertificateQueryParameters2 = null;
    }

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Test
    void addCorrectDataShouldReturnGiftCertificateTest() {
        // when
        GiftCertificate actual = giftCertificateDao.add(giftCertificate1);

        // then
        assertNotNull(actual);
    }

    @Test
    void addCorrectDataShouldSetIdTest() {
        // given
        long expected = 6;

        // when
        giftCertificateDao.add(giftCertificate1);
        long actual = giftCertificate1.getId();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void addIncorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.add(giftCertificate2));
    }

    @Test
    void findAllShouldReturnListOfGiftCertificatesTest() {
        // given
        long expected = 6;

        // when
        List<GiftCertificate> actual = giftCertificateDao.findAll();

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findByIdCorrectDataShouldReturnGiftCertificateOptionalTest() {
        // given
        long id = 1;

        // when
        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);

        // then
        assertEquals(Optional.of(giftCertificate3), actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnEmptyOptionalTest() {
        // given
        long id = 7;

        // when
        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);

        // then
        assertFalse(actual.isPresent());
    }

    @Test
    void updateCorrectDataShouldReturnGiftCertificateTest() {
        // when
        GiftCertificate actual = giftCertificateDao.update(giftCertificate3);

        // then
        assertEquals(giftCertificate3, actual);
    }

    @Test
    void updateIncorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.update(giftCertificate4));
    }

    @Test
    void removeCorrectDataShouldNotThrowExceptionTest() {
        // given
        long id = 6;

        // then
        assertDoesNotThrow(() -> giftCertificateDao.remove(id));
    }

    @Test
    void removeGiftCertificateHasTagCorrectDataShouldNotThrowExceptionTest() {
        // given
        long id = 6;

        // then
        assertDoesNotThrow(() -> giftCertificateDao.removeGiftCertificateHasTag(id));
    }

    @Test
    void addGiftCertificateHasTagCorrectDataShouldNotThrowExceptionTest() {
        // then
        assertDoesNotThrow(() -> giftCertificateDao.addGiftCertificateHasTag(giftCertificate5));
    }

    @Test
    void findByQueryParametersCorrectDataShouldReturnListOfGiftCertificatesTest() {
        // given
        int expected = 2;

        // when
        List<GiftCertificate> actual = giftCertificateDao.findByQueryParameters(giftCertificateQueryParameters1);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findByQueryParametersCorrectDataShouldReturnEmptyListTest() {
        // when
        List<GiftCertificate> actual = giftCertificateDao.findByQueryParameters(giftCertificateQueryParameters2);

        // then
        assertTrue(actual.isEmpty());
    }
}
