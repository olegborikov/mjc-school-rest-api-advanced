package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {
    void setGiftCertificateDao(GiftCertificateDao giftCertificateDao);

    boolean addGiftCertificate(GiftCertificate giftCertificate);

    List<GiftCertificate> findAllGiftCertificates();

    Optional<GiftCertificate> findGiftCertificateById(long id);

    boolean updateGiftCertificate(GiftCertificate giftCertificate);

    boolean removeGiftCertificate(long id);
}
