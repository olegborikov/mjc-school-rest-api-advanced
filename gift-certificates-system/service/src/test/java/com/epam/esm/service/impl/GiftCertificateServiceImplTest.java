package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.GiftCertificateQueryParameters;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

class GiftCertificateServiceImplTest {

    private static GiftCertificateDao giftCertificateDao;
    private static ModelMapper modelMapper;
    private static GiftCertificateService giftCertificateService;
    private static TagService tagService;
    private static GiftCertificate giftCertificate1;
    private static GiftCertificate giftCertificate2;
    private static GiftCertificateDto giftCertificateDto1;
    private static GiftCertificateDto giftCertificateDto2;
    private static GiftCertificateDto giftCertificateDto3;
    private static GiftCertificateDto giftCertificateDto4;
    private static GiftCertificateDto giftCertificateDto5;
    private static GiftCertificateDto giftCertificateDto6;
    private static GiftCertificateDto giftCertificateDto7;
    private static GiftCertificateQueryParametersDto giftCertificateQueryParametersDto1;

    @BeforeAll
    static void setUp() {
        giftCertificateDao = mock(GiftCertificateDaoImpl.class);
        tagService = mock(TagServiceImpl.class);
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, tagService, modelMapper);
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
                .id(1L)
                .name("Cinema")
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        giftCertificateDto6 = GiftCertificateDto.builder()
                .id(1L)
                .name("Best cinema in the city")
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        giftCertificateDto7 = GiftCertificateDto.builder()
                .id(1L)
                .name("Cinema")
                .duration(5)
                .description("Nice")
                .price(new BigDecimal("-1"))
                .build();
        giftCertificateQueryParametersDto1 = GiftCertificateQueryParametersDto.builder()
                .tagName("Sport")
                .name("o")
                .description("i")
                .sortType(GiftCertificateQueryParametersDto.SortType.NAME)
                .orderType(GiftCertificateQueryParametersDto.OrderType.DESC)
                .build();
    }

    @AfterAll
    static void tearDown() {
        giftCertificateDao = null;
        modelMapper = null;
        giftCertificateService = null;
        tagService = null;
        giftCertificate1 = null;
        giftCertificate2 = null;
        giftCertificateDto1 = null;
        giftCertificateDto2 = null;
        giftCertificateDto3 = null;
        giftCertificateDto4 = null;
        giftCertificateDto5 = null;
        giftCertificateDto6 = null;
        giftCertificateDto7 = null;
        giftCertificateQueryParametersDto1 = null;
    }

    @Test
    void addGiftCertificateCorrectDataShouldReturnGiftCertificateDtoTest() {
        // given
        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate1);
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));

        // when
        GiftCertificateDto actual = giftCertificateService.addGiftCertificate(giftCertificateDto1);

        // then
        assertEquals(giftCertificateDto2, actual);
    }

    @Test
    void addGiftCertificateIncorrectDataShouldThrowExceptionTest() {
        // given
        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate1);
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));

        // then
        assertThrows(IncorrectParameterValueException.class,
                () -> giftCertificateService.addGiftCertificate(giftCertificateDto3));
    }

    @Test
    void findGiftCertificatesCorrectDataShouldReturnListOfGiftCertificateDtoTest() {
        // given
        int expected = 2;
        when(giftCertificateDao.findByQueryParameters(any(GiftCertificateQueryParameters.class)))
                .thenReturn(Arrays.asList(giftCertificate1, giftCertificate2));
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());

        // when
        List<GiftCertificateDto> actual
                = giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto1);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findGiftCertificateByIdCorrectDataShouldReturnGiftCertificateDtoTest() {
        // given
        long id = 1;
        when(giftCertificateDao.findById(any(Long.class))).thenReturn(Optional.of(giftCertificate2));
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());

        // when
        GiftCertificateDto actual = giftCertificateService.findGiftCertificateById(id);

        // then
        assertEquals(giftCertificateDto4, actual);
    }

    @Test
    void findGiftCertificateByIdCorrectDataShouldThrowExceptionTest() {
        // given
        long id = 5;
        when(giftCertificateDao.findById(any(Long.class))).thenReturn(Optional.empty());
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());

        // then
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    void findGiftCertificateByIdIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = -1;
        when(giftCertificateDao.findById(any(Long.class))).thenReturn(Optional.of(giftCertificate1));
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());

        // then
        assertThrows(IncorrectParameterValueException.class, () -> giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    void updateGiftCertificateCorrectDataShouldReturnGiftCertificateDtoTest() {
        // given
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate2));
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate1);
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));

        // when
        GiftCertificateDto actual = giftCertificateService.updateGiftCertificate(giftCertificateDto5);

        // then
        assertEquals(giftCertificateDto2, actual);
    }

    @Test
    void updateGiftCertificateCorrectDataShouldThrowExceptionTest() {
        // given
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.empty());
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate1);
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.updateGiftCertificate(giftCertificateDto6));
    }

    @Test
    void updateGiftCertificateIncorrectDataShouldThrowExceptionTest() {
        // given
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate2));
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate1);
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));

        // then
        assertThrows(IncorrectParameterValueException.class,
                () -> giftCertificateService.updateGiftCertificate(giftCertificateDto7));
    }

    @Test
    void removeGiftCertificateCorrectDataShouldNotThrowExceptionTest() {
        // given
        long id = 1;
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).remove(any(Long.class));

        // then
        assertDoesNotThrow(() -> giftCertificateService.removeGiftCertificate(id));
    }

    @Test
    void removeGiftCertificateIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = -1;
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).remove(any(Long.class));

        // then
        assertThrows(IncorrectParameterValueException.class, () -> giftCertificateService.removeGiftCertificate(id));
    }
}
