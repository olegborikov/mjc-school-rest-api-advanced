package com.epam.esm.service.impl;

import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceConfiguration.class)
@Import(ValidationAutoConfiguration.class)
class GiftCertificateServiceImplTest {

    @MockBean
    private GiftCertificateDao giftCertificateDao;
    @MockBean
    private TagService tagService;
    @Autowired
    private GiftCertificateService giftCertificateService;
    private static GiftCertificate giftCertificate1;
    private static GiftCertificate giftCertificate2;
    private static GiftCertificateDto giftCertificateDto1;
    private static GiftCertificateDto giftCertificateDto2;
    private static GiftCertificateDto giftCertificateDto3;
    private static GiftCertificateDto giftCertificateDto4;
    private static GiftCertificateDto giftCertificateDto5;
    private static GiftCertificateDto giftCertificateDto6;
    private static GiftCertificateQueryParametersDto giftCertificateQueryParametersDto1;
    private static PageDto pageDto1;
    private static PageDto pageDto2;

    @BeforeAll
    static void setUp() {
        giftCertificate1 = GiftCertificate.builder()
                .id(1L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        giftCertificate2 = GiftCertificate.builder()
                .id(1L)
                .name("Travel to German")
                .description("You will like it")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        giftCertificateDto1 = GiftCertificateDto.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        giftCertificateDto2 = GiftCertificateDto.builder()
                .id(1L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        giftCertificateDto3 = GiftCertificateDto.builder()
                .name("Cinema")
                .description("You will like it")
                .price(new BigDecimal(100))
                .duration(-5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        giftCertificateDto4 = GiftCertificateDto.builder()
                .id(1L)
                .name("Travel to German")
                .description("You will like it")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        giftCertificateDto5 = GiftCertificateDto.builder()
                .name("Cinema")
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        giftCertificateDto6 = GiftCertificateDto.builder()
                .name("Cinema")
                .duration(5)
                .description("Nice")
                .price(new BigDecimal("-1"))
                .build();
        giftCertificateQueryParametersDto1 = GiftCertificateQueryParametersDto.builder()
                .tagNames(new String[]{"Sport"})
                .name("o")
                .description("i")
                .sortType(GiftCertificateQueryParametersDto.SortType.NAME)
                .orderType(GiftCertificateQueryParametersDto.OrderType.DESC)
                .build();
        pageDto1 = PageDto.builder()
                .number(1)
                .size(5)
                .build();
        pageDto2 = PageDto.builder()
                .number(-3)
                .size(3)
                .build();
    }

    @AfterAll
    static void tearDown() {
        giftCertificate1 = null;
        giftCertificate2 = null;
        giftCertificateDto1 = null;
        giftCertificateDto2 = null;
        giftCertificateDto3 = null;
        giftCertificateDto4 = null;
        giftCertificateDto5 = null;
        giftCertificateDto6 = null;
        giftCertificateQueryParametersDto1 = null;
        pageDto1 = null;
        pageDto2 = null;
    }

    @Test
    void addGiftCertificateCorrectDataShouldReturnGiftCertificateDtoTest() {
        // given
        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate1);

        // when
        GiftCertificateDto actual = giftCertificateService.addGiftCertificate(giftCertificateDto1);

        // then
        assertEquals(giftCertificateDto2, actual);
    }

    @Test
    void addGiftCertificateIncorrectDataShouldThrowExceptionTest() {
        // given
        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate1);

        // then
        assertThrows(ConstraintViolationException.class,
                () -> giftCertificateService.addGiftCertificate(giftCertificateDto3));
    }

    @Test
    void findGiftCertificatesByQueryParametersCorrectDataShouldReturnListOfGiftCertificateDtoTest() {
        // given
        int expected = 2;
        when(giftCertificateDao.findByQueryParameters(any(GiftCertificateQueryParameters.class), any(Page.class)))
                .thenReturn(Arrays.asList(giftCertificate1, giftCertificate2));

        // when
        List<GiftCertificateDto> actual = giftCertificateService.findGiftCertificatesByQueryParameters(
                giftCertificateQueryParametersDto1, pageDto1);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findGiftCertificatesByQueryParametersIncorrectDataShouldThrowExceptionTest() {
        // given
        when(giftCertificateDao.findByQueryParameters(any(GiftCertificateQueryParameters.class), any(Page.class)))
                .thenReturn(Arrays.asList(giftCertificate1, giftCertificate2));

        // then
        assertThrows(ConstraintViolationException.class,
                () -> giftCertificateService.findGiftCertificatesByQueryParameters(
                        giftCertificateQueryParametersDto1, pageDto2));
    }

    @Test
    void findGiftCertificateByIdCorrectDataShouldReturnGiftCertificateDtoTest() {
        // given
        long id = 1;
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate2));

        // when
        GiftCertificateDto actual = giftCertificateService.findGiftCertificateById(id);

        // then
        assertEquals(giftCertificateDto4, actual);
    }

    @Test
    void findGiftCertificateByIdCorrectDataShouldThrowExceptionTest() {
        // given
        long id = 5;
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    void findGiftCertificateByIdIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = -1;
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate1));

        // then
        assertThrows(ConstraintViolationException.class, () -> giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    void updateGiftCertificateCorrectDataShouldReturnGiftCertificateDtoTest() {
        // given
        long id = 1;
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate2));
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate1);

        // when
        GiftCertificateDto actual = giftCertificateService.updateGiftCertificate(id, giftCertificateDto5);

        // then
        assertEquals(giftCertificateDto2, actual);
    }

    @Test
    void updateGiftCertificateIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = 1;
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate2));
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate1);

        // then
        assertThrows(ConstraintViolationException.class,
                () -> giftCertificateService.updateGiftCertificate(id, giftCertificateDto6));
    }

    @Test
    void removeGiftCertificateCorrectDataShouldNotThrowExceptionTest() {
        // given
        long id = 1;
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate1));
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(long.class));
        doNothing().when(giftCertificateDao).remove(any(long.class));

        // then
        assertDoesNotThrow(() -> giftCertificateService.removeGiftCertificate(id));
    }

    @Test
    void removeGiftCertificateIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = -1;
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate1));
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(long.class));
        doNothing().when(giftCertificateDao).remove(any(long.class));

        // then
        assertThrows(ConstraintViolationException.class, () -> giftCertificateService.removeGiftCertificate(id));
    }
}
