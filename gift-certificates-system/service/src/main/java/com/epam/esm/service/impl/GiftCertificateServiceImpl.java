package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    @Override
    public void setGiftCertificateDao(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public boolean addGiftCertificate(GiftCertificate giftCertificate) {
        boolean isAdded = false;
        if (giftCertificate.getDuration() > 0
                && GiftCertificateValidator.isNameCorrect(giftCertificate.getName())
                && GiftCertificateValidator.isDescriptionCorrect(giftCertificate.getDescription())
                && GiftCertificateValidator.isPriceCorrect(giftCertificate.getPrice())
                && giftCertificate.getCreateDate().isBefore(giftCertificate.getLastUpdateDate())) {
            isAdded = giftCertificateDao.add(giftCertificate);
        }
        return isAdded;
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificates() {
        return giftCertificateDao.findAll();
    }

    @Override
    public Optional<GiftCertificate> findGiftCertificateById(long id) {
        Optional<GiftCertificate> foundGiftCertificate = Optional.empty();
        if (id > 0) {
            foundGiftCertificate = giftCertificateDao.findById(id);
        }
        return foundGiftCertificate;
    }

    @Override
    public boolean updateGiftCertificate(GiftCertificate giftCertificate) {
        boolean isUpdated = false;
        if (giftCertificate.getId() > 0 && giftCertificate.getDuration() > 0
                && GiftCertificateValidator.isNameCorrect(giftCertificate.getName())
                && GiftCertificateValidator.isDescriptionCorrect(giftCertificate.getDescription())
                && GiftCertificateValidator.isPriceCorrect(giftCertificate.getPrice())) {
            isUpdated = giftCertificateDao.add(giftCertificate);
        }
        return isUpdated;
    }

    @Override
    public boolean removeGiftCertificate(long id) {
        boolean isRemoved = false;
        if (id > 0) {
            isRemoved = giftCertificateDao.remove(id);
        }
        return isRemoved;
    }
}
