package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {
    void setGiftCertificateDao(GiftCertificateDao giftCertificateDao);

    GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> findAllGiftCertificates();

    GiftCertificateDto findGiftCertificateById(String id);

    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    void removeGiftCertificate(String id);
}
