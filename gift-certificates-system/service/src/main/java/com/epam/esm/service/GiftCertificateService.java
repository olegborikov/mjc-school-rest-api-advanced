package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.group.AddGroup;
import com.epam.esm.dto.group.UpdateGroup;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
public interface GiftCertificateService {

    @Validated(AddGroup.class)
    GiftCertificateDto addGiftCertificate(@Valid GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> findGiftCertificates(// TODO: 26.01.2021 rename
            GiftCertificateQueryParametersDto giftCertificateQueryParametersDto, @Valid PageDto pageDto);

    GiftCertificateDto findGiftCertificateById(
            @Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_ID) long id)
            throws ResourceNotFoundException;

    @Validated(UpdateGroup.class)
    GiftCertificateDto updateGiftCertificate(
            @Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_ID) long id,
            GiftCertificateDto giftCertificateDto);

    void removeGiftCertificate(
            @Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_ID) long id);
}
