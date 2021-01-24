package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface GiftCertificateService {

    GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto)
            throws IncorrectParameterValueException;

    List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParametersDto giftCertificateQueryParametersDto, @Valid PageDto pageDto);

    GiftCertificateDto findGiftCertificateById(long id)
            throws IncorrectParameterValueException, ResourceNotFoundException;

    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto)
            throws IncorrectParameterValueException;

    void removeGiftCertificate(long id) throws IncorrectParameterValueException;
}
