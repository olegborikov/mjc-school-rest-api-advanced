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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        GiftCertificateDto expected = GiftCertificateDto.builder()
                .id(1L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate);
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));
        GiftCertificateDto actual = giftCertificateService.addGiftCertificate(giftCertificateDto);
        assertEquals(expected, actual);
    }

    @Test
    void addGiftCertificateIncorrectDataShouldThrowException() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Travel")
                .description("qwerty")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(-5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate);
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));
        assertThrows(IncorrectParameterValueException.class, () ->
                giftCertificateService.addGiftCertificate(giftCertificateDto));
    }

    @Test
    void findGiftCertificatesCorrectDataShouldReturnListOfGiftCertificateDto() {
        GiftCertificate giftCertificate1 = GiftCertificate.builder()
                .id(1L)
                .name("Travel")
                .description("qwerty")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        GiftCertificate giftCertificate2 = GiftCertificate.builder()
                .id(2L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        int expected = 2;
        when(giftCertificateDao.findByQueryParameters(any(GiftCertificateQueryParameters.class)))
                .thenReturn(Arrays.asList(giftCertificate1, giftCertificate2));
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());
        GiftCertificateQueryParametersDto giftCertificateQueryParametersDto
                = GiftCertificateQueryParametersDto.builder()
                .tagName("Sport")
                .name("o")
                .description("i")
                .sortType(GiftCertificateQueryParametersDto.SortType.NAME)
                .orderType(GiftCertificateQueryParametersDto.OrderType.DESC)
                .build();
        List<GiftCertificateDto> actual
                = giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto);
        assertEquals(expected, actual.size());
    }

    @Test
    void findGiftCertificateByIdCorrectDataShouldReturnGiftCertificateDto() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Travel")
                .description("qwerty")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificateDto expected = GiftCertificateDto.builder()
                .id(1L)
                .name("Travel")
                .description("qwerty")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        when(giftCertificateDao.findById(any(Long.class))).thenReturn(Optional.of(giftCertificate));
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());
        Long id = 1L;
        GiftCertificateDto actual = giftCertificateService.findGiftCertificateById(id);
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateByIdCorrectDataShouldThrowException() {
        when(giftCertificateDao.findById(any(Long.class))).thenReturn(Optional.empty());
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());
        Long id = 5L;
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    void findGiftCertificateByIdIncorrectDataShouldThrowException() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Travel")
                .description("qwerty")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        when(giftCertificateDao.findById(any(Long.class))).thenReturn(Optional.of(giftCertificate));
        when(tagService.findTagsByGiftCertificateId(any(Long.class))).thenReturn(new ArrayList<>());
        Long id = -1L;
        assertThrows(IncorrectParameterValueException.class, () -> giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    void updateGiftCertificateCorrectDataShouldReturnGiftCertificateDto() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Travel")
                .description("qwerty")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificate giftCertificate1 = GiftCertificate.builder()
                .id(1L)
                .name("qwerty")
                .description("qwerty")
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
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("qwerty")
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificateDto expected = GiftCertificateDto.builder()
                .id(1L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(500))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        GiftCertificateDto actual = giftCertificateService.updateGiftCertificate(giftCertificateDto);
        assertEquals(expected, actual);
    }

    @Test
    void updateGiftCertificateCorrectDataShouldThrowException() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(500))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.empty());
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate);
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).addGiftCertificateHasTag(any(GiftCertificate.class));
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("qwerty")
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        assertThrows(ResourceNotFoundException.class, () ->
                giftCertificateService.updateGiftCertificate(giftCertificateDto));
    }

    @Test
    void updateGiftCertificateIncorrectDataShouldThrowException() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("Travel")
                .description("qwerty")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificate giftCertificate1 = GiftCertificate.builder()
                .id(1L)
                .name("qwerty")
                .description("qwerty")
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
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("qwerty")
                .duration(5)
                .description("Nice")
                .price(new BigDecimal("1"))
                .createDate(LocalDateTime.of(2021, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        assertThrows(IncorrectParameterValueException.class, () ->
                giftCertificateService.updateGiftCertificate(giftCertificateDto));
    }

    @Test
    void removeGiftCertificateCorrectDataShouldNotThrowException() {
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).remove(any(Long.class));
        Long id = 1L;
        assertDoesNotThrow(() -> giftCertificateService.removeGiftCertificate(id));
    }

    @Test
    void removeGiftCertificateIncorrectDataShouldThrowException() {
        doNothing().when(giftCertificateDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(giftCertificateDao).remove(any(Long.class));
        Long id = -1L;
        assertThrows(IncorrectParameterValueException.class, () -> giftCertificateService.removeGiftCertificate(id));
    }
}
