package com.epam.esm.dao.impl;

import com.epam.esm.configuration.PersistenceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.util.OrderType;
import com.epam.esm.util.Page;
import com.epam.esm.util.SortType;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
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
class GiftCertificateDaoImplTest {

    private final GiftCertificateDao giftCertificateDao;
    private static GiftCertificate giftCertificate1;
    private static GiftCertificate giftCertificate2;
    private static GiftCertificate giftCertificate3;
    private static GiftCertificate giftCertificate4;
    private static GiftCertificateQueryParameters giftCertificateQueryParameters1;
    private static GiftCertificateQueryParameters giftCertificateQueryParameters2;
    private static Page page1;
    private static Page page2;
    private static Page page3;

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

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
                .price(new BigDecimal("100.10"))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 8, 2, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2021, 1, 2, 16, 0, 0))
                .tags(Arrays.asList(new Tag(1L, "home"), new Tag(2L, "school")))
                .build();
        giftCertificate4 = GiftCertificate.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        giftCertificateQueryParameters1 = GiftCertificateQueryParameters.builder()
                .tagNames(new String[]{"school"})
                .name("a")
                .description("i")
                .sortType(SortType.NAME)
                .orderType(OrderType.ASC)
                .build();
        giftCertificateQueryParameters2 = GiftCertificateQueryParameters.builder()
                .tagNames(new String[]{"school1"})
                .name("a")
                .description("i")
                .sortType(SortType.CREATE_DATE)
                .orderType(OrderType.DESC)
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
        giftCertificate1 = null;
        giftCertificate2 = null;
        giftCertificate3 = null;
        giftCertificate4 = null;
        giftCertificateQueryParameters1 = null;
        giftCertificateQueryParameters2 = null;
        page1 = null;
        page2 = null;
        page3 = null;
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
        giftCertificateDao.add(giftCertificate4);

        // then
        assertEquals(expected, giftCertificate4.getId());
    }

    @Test
    void addCorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> giftCertificateDao.add(giftCertificate3));
    }

    @Test
    void addIncorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.add(giftCertificate2));
    }

    @Test
    void findAllCorrectDataShouldReturnListOfGiftCertificatesTest() {
        // given
        long expected = 4;

        // when
        List<GiftCertificate> actual = giftCertificateDao.findAll(page1);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findAllCorrectDataShouldReturnEmptyListTest() {
        // when
        List<GiftCertificate> actual = giftCertificateDao.findAll(page2);

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    void findAllIncorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> giftCertificateDao.findAll(page3));
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
    void removeCorrectDataShouldNotThrowExceptionTest() {
        // given
        long id = 4;

        // then
        assertDoesNotThrow(() -> giftCertificateDao.remove(id));
    }

    @Test
    void removeIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = 10;

        // then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> giftCertificateDao.remove(id));
    }

    @Test
    void removeGiftCertificateHasTagCorrectDataShouldNotThrowExceptionTest() {
        // given
        long id = 6;

        // then
        assertDoesNotThrow(() -> giftCertificateDao.removeGiftCertificateHasTag(id));
    }

    @Test
    void findByQueryParametersCorrectDataShouldReturnListOfGiftCertificatesTest() {
        // given
        int expected = 2;

        // when
        List<GiftCertificate> actual
                = giftCertificateDao.findByQueryParameters(giftCertificateQueryParameters1, page1);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findByQueryParametersCorrectDataShouldReturnEmptyListTest() {
        // when
        List<GiftCertificate> actual
                = giftCertificateDao.findByQueryParameters(giftCertificateQueryParameters2, page1);

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    void findByQueryParametersIncorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> giftCertificateDao.findByQueryParameters(giftCertificateQueryParameters2, page3));
    }
}
