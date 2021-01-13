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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    private GiftCertificateDao giftCertificateDao;
    private ModelMapper modelMapper;
    private GiftCertificateService giftCertificateService;
    private TagService tagService;

    @BeforeEach
    void setUp() {
        giftCertificateDao = mock(GiftCertificateDaoImpl.class);
        tagService = mock(TagServiceImpl.class);
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, tagService, modelMapper);
    }

    @AfterEach
    void tearDown() {
        giftCertificateDao = null;
        modelMapper = null;
        giftCertificateService = null;
        tagService = null;
    }

    @Test
    void addGiftCertificateCorrectDataShouldReturnGiftCertificateDto() {
        // given
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        GiftCertificateDto expected = GiftCertificateDto.builder()
                .id(1L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate);
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));

        // when
        GiftCertificateDto actual = giftCertificateService.addGiftCertificate(giftCertificateDto);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void addGiftCertificateIncorrectDataShouldThrowException() {
        // given
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Travel to German")
                .description("You will like it")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .name("Cinema")
                .description("You will like it")
                .price(new BigDecimal(100))
                .duration(-5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate);
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));

        // then
        assertThrows(IncorrectParameterValueException.class,
                () -> giftCertificateService.addGiftCertificate(giftCertificateDto));
    }

    @Test
    void findGiftCertificatesCorrectDataShouldReturnListOfGiftCertificateDto() {
        // given
        GiftCertificate giftCertificate1 = GiftCertificate.builder()
                .id(1L)
                .name("Travel to German")
                .description("You will like it")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        GiftCertificate giftCertificate2 = GiftCertificate.builder()
                .id(2L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        GiftCertificateQueryParametersDto giftCertificateQueryParametersDto
                = GiftCertificateQueryParametersDto.builder()
                .tagName("Sport")
                .name("o")
                .description("i")
                .sortType(GiftCertificateQueryParametersDto.SortType.NAME)
                .orderType(GiftCertificateQueryParametersDto.OrderType.DESC)
                .build();
        int expected = 2;
        when(giftCertificateDao.findByQueryParameters(any(GiftCertificateQueryParameters.class)))
                .thenReturn(Arrays.asList(giftCertificate1, giftCertificate2));
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());

        // when
        List<GiftCertificateDto> actual
                = giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findGiftCertificateByIdCorrectDataShouldReturnGiftCertificateDto() {
        // given
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Travel to German")
                .description("You will like it")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificateDto expected = GiftCertificateDto.builder()
                .id(1L)
                .name("Travel to German")
                .description("You will like it")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        Long id = 1L;
        when(giftCertificateDao.findById(any(Long.class))).thenReturn(Optional.of(giftCertificate));
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());

        // when
        GiftCertificateDto actual = giftCertificateService.findGiftCertificateById(id);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateByIdCorrectDataShouldThrowException() {
        // given
        Long id = 5L;
        when(giftCertificateDao.findById(any(Long.class))).thenReturn(Optional.empty());
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());

        // then
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    void findGiftCertificateByIdIncorrectDataShouldThrowException() {
        // given
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Travel to German")
                .description("You will like it")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        Long id = -1L;
        when(giftCertificateDao.findById(any(Long.class))).thenReturn(Optional.of(giftCertificate));
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());

        // then
        assertThrows(IncorrectParameterValueException.class, () -> giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    void updateGiftCertificateCorrectDataShouldReturnGiftCertificateDto() {
        // given
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Travel to German")
                .description("You will like it")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificate giftCertificate1 = GiftCertificate.builder()
                .id(1L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(500))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("Cinema")
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificateDto expected = GiftCertificateDto.builder()
                .id(1L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(500))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate1);
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));

        // when
        GiftCertificateDto actual = giftCertificateService.updateGiftCertificate(giftCertificateDto);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void updateGiftCertificateCorrectDataShouldThrowException() {
        // given
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(500))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("Best cinema in the city")
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.empty());
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate);
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.updateGiftCertificate(giftCertificateDto));
    }

    @Test
    void updateGiftCertificateIncorrectDataShouldThrowException() {
        // given
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Travel to German")
                .description("You will ike it")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificate giftCertificate1 = GiftCertificate.builder()
                .id(1L)
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(500))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("Cinema")
                .duration(5)
                .description("Nice")
                .price(new BigDecimal("-1"))
                .build();
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate1);
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));

        // then
        assertThrows(IncorrectParameterValueException.class,
                () -> giftCertificateService.updateGiftCertificate(giftCertificateDto));
    }

    @Test
    void removeGiftCertificateCorrectDataShouldNotThrowException() {
        // given
        Long id = 1L;
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).remove(any(Long.class));

        // then
        assertDoesNotThrow(() -> giftCertificateService.removeGiftCertificate(id));
    }

    @Test
    void removeGiftCertificateIncorrectDataShouldThrowException() {
        // given
        Long id = -1L;
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).remove(any(Long.class));

        // then
        assertThrows(IncorrectParameterValueException.class, () -> giftCertificateService.removeGiftCertificate(id));
    }
}
