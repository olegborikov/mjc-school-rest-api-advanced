package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateDao giftCertificateDao;
    private ModelMapper modelMapper;

    @Autowired
    @Override
    public void setGiftCertificateDao(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        if (giftCertificate.getDuration() > 0
                && GiftCertificateValidator.isNameCorrect(giftCertificate.getName())
                && GiftCertificateValidator.isDescriptionCorrect(giftCertificate.getDescription())
                && GiftCertificateValidator.isPriceCorrect(giftCertificate.getPrice())
                && giftCertificate.getCreateDate().isBefore(giftCertificate.getLastUpdateDate())) {
            GiftCertificate addedGiftCertificate = giftCertificateDao.add(giftCertificate);
            return modelMapper.map(addedGiftCertificate, GiftCertificateDto.class);
        } else {
            return null;// TODO: 04.01.2021
        }
    }

    @Override
    public List<GiftCertificateDto> findAllGiftCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto findGiftCertificateById(long id) {
        if (id > 0) {
            Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);
            return foundGiftCertificate
                    .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                    .orElse(null); // TODO: 04.01.2021
        } else {
            return null; // TODO: 04.01.2021
        }
    }

    @Override
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        if (giftCertificate.getDuration() > 0
                && GiftCertificateValidator.isNameCorrect(giftCertificate.getName())
                && GiftCertificateValidator.isDescriptionCorrect(giftCertificate.getDescription())
                && GiftCertificateValidator.isPriceCorrect(giftCertificate.getPrice())
                && giftCertificate.getCreateDate().isBefore(giftCertificate.getLastUpdateDate())) {
            boolean isUpdated = giftCertificateDao.update(giftCertificate);
            if (isUpdated) {
                return giftCertificateDto;
            } else {
                return null;// TODO: 04.01.2021
            }
        } else {
            return null;// TODO: 04.01.2021
        }
    }

    @Override
    public void removeGiftCertificate(long id) {
        if (id > 0) {
            boolean isRemoved = giftCertificateDao.remove(id);
            if (!isRemoved) {
                // TODO: 04.01.2021
            }
        } else {
            // TODO: 04.01.2021
        }
    }
}
