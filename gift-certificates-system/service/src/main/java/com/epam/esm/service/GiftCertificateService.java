package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> findGiftCertificates(GiftCertificateQueryParametersDto giftCertificateQueryParametersDto);

    GiftCertificateDto findGiftCertificateById(Long id);

    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    void removeGiftCertificate(Long id);
}
