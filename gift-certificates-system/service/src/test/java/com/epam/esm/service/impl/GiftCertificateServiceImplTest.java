package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.IncorrectParametersValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GiftCertificateServiceImplTest {
    private GiftCertificateDao giftCertificateDao;
    private ModelMapper modelMapper;
    private GiftCertificateService giftCertificateService;

    @BeforeEach
    void setUp() {
        giftCertificateDao = mock(GiftCertificateDaoImpl.class);
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, modelMapper);
    }

    @AfterEach
    void tearDown() {
        giftCertificateDao = null;
        modelMapper = null;
        giftCertificateService = null;
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
                .build();
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificateDto expected = GiftCertificateDto.builder()
                .id(1L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate);
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
        assertThrows(IncorrectParametersValueException.class, () -> {
            giftCertificateService.addGiftCertificate(giftCertificateDto);
        });
    }

    @Test
    void findAllGiftCertificatesCorrectDataShouldReturnListOfGiftCertificateDto() {
        GiftCertificate giftCertificate1 = GiftCertificate.builder()
                .id(1L)
                .name("Travel")
                .description("qwerty")
                .price(new BigDecimal(500))
                .duration(1)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificate giftCertificate2 = GiftCertificate.builder()
                .id(2L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        int expected = 2;
        when(giftCertificateDao.findAll()).thenReturn(Arrays.asList(giftCertificate1, giftCertificate2));
        List<GiftCertificateDto> actual = giftCertificateService.findAllGiftCertificates();
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
                .build();
        when(giftCertificateDao.findById(any(Long.class))).thenReturn(Optional.of(giftCertificate));
        String id = "1";
        GiftCertificateDto actual = giftCertificateService.findGiftCertificateById(id);
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateByIdCorrectDataShouldThrowException() {
        when(giftCertificateDao.findById(any(Long.class))).thenReturn(Optional.empty());
        String id = "1";
        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.findGiftCertificateById(id);
        });
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
        String id = "a";
        assertThrows(IncorrectParametersValueException.class, () -> {
            giftCertificateService.findGiftCertificateById(id);
        });
    }

    @Test
    void updateGiftCertificateCorrectDataShouldReturnGiftCertificateDto() {
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        GiftCertificateDto expected = GiftCertificateDto.builder()
                .id(1L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(true);
        GiftCertificateDto actual = giftCertificateService.updateGiftCertificate(giftCertificateDto);
        assertEquals(expected, actual);
    }

    @Test
    void updateGiftCertificateCorrectDataShouldThrowException() {
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("qwerty")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.updateGiftCertificate(giftCertificateDto);
        });
    }

    @Test
    void updateGiftCertificateIncorrectDataShouldThrowException() {
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name(" ")
                .description("qwerty")
                .price(new BigDecimal(100))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(true);
        assertThrows(IncorrectParametersValueException.class, () -> {
            giftCertificateService.updateGiftCertificate(giftCertificateDto);
        });
    }

    @Test
    void removeGiftCertificateCorrectDataShouldNotThrowException() {
        when(giftCertificateDao.remove(any(Long.class))).thenReturn(true);
        String id = "1";
        giftCertificateService.removeGiftCertificate(id);
    }

    @Test
    void removeGiftCertificateCorrectDataShouldThrowException() {
        when(giftCertificateDao.remove(any(Long.class))).thenReturn(false);
        String id = "5";
        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.removeGiftCertificate(id);
        });
    }

    @Test
    void removeGiftCertificateIncorrectDataShouldThrowException() {
        when(giftCertificateDao.remove(any(Long.class))).thenReturn(true);
        String id = "a";
        assertThrows(IncorrectParametersValueException.class, () -> {
            giftCertificateService.removeGiftCertificate(id);
        });
    }
}
