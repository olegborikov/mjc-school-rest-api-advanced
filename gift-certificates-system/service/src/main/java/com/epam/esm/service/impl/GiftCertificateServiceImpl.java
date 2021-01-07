package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.validator.GiftCertificateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, ModelMapper modelMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        GiftCertificateValidator.validateDuration(giftCertificate.getDuration());
        GiftCertificateValidator.validateName(giftCertificate.getName());
        GiftCertificateValidator.validateDescription(giftCertificate.getDescription());
        GiftCertificateValidator.validatePrice(giftCertificate.getPrice());
        GiftCertificateValidator.validateDates(giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate());
        GiftCertificate addedGiftCertificate = giftCertificateDao.add(giftCertificate);
        return modelMapper.map(addedGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParametersDto giftCertificateQueryParametersDto) {
        GiftCertificateQueryParameters giftCertificateQueryParameters
                = modelMapper.map(giftCertificateQueryParametersDto, GiftCertificateQueryParameters.class);
        List<GiftCertificate> giftCertificates
                = giftCertificateDao.findByQueryParameters(giftCertificateQueryParameters);
        giftCertificates.forEach(giftCertificate ->
                giftCertificate.setTags(tagDao.findByGiftCertificateId(giftCertificate.getId())));
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto findGiftCertificateById(Long id) {
        GiftCertificateValidator.validateId(id);
        Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);
        return foundGiftCertificate
                .map(giftCertificate -> {
                    giftCertificate.setTags(tagDao.findByGiftCertificateId(giftCertificate.getId()));
                    return modelMapper.map(giftCertificate, GiftCertificateDto.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Gift certificate with id " + id + " not found."));
    }

    @Override
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate receivedGiftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        GiftCertificateValidator.validateId(receivedGiftCertificate.getId());
        Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(receivedGiftCertificate.getId());
        if (foundGiftCertificate.isPresent()) {
            GiftCertificate newGiftCertificate
                    = updateFieldsGiftCertificate(receivedGiftCertificate, foundGiftCertificate.get());
            GiftCertificate updatedGiftCertificate = giftCertificateDao.update(newGiftCertificate);
            return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
        } else {
            throw new ResourceNotFoundException("Gift certificate with id "
                    + receivedGiftCertificate.getId() + " not found.");
        }
    }

    @Transactional
    @Override
    public void removeGiftCertificate(Long id) {
        GiftCertificateValidator.validateId(id);
        giftCertificateDao.removeGiftCertificateHasTag(id);
        giftCertificateDao.remove(id);
    }

    private GiftCertificate updateFieldsGiftCertificate(GiftCertificate receivedGiftCertificate,
                                                        GiftCertificate foundGiftCertificate) {
        if (receivedGiftCertificate.getDuration() != 0) {
            GiftCertificateValidator.validateDuration(receivedGiftCertificate.getDuration());
            foundGiftCertificate.setDuration(receivedGiftCertificate.getDuration());
        }
        if (receivedGiftCertificate.getName() != null) {
            GiftCertificateValidator.validateName(receivedGiftCertificate.getName());
            foundGiftCertificate.setName(receivedGiftCertificate.getName());
        }
        if (receivedGiftCertificate.getDescription() != null) {
            GiftCertificateValidator.validateDescription(receivedGiftCertificate.getDescription());
            foundGiftCertificate.setDescription(receivedGiftCertificate.getDescription());
        }
        if (receivedGiftCertificate.getPrice() != null) {
            GiftCertificateValidator.validatePrice(receivedGiftCertificate.getPrice());
            foundGiftCertificate.setPrice(receivedGiftCertificate.getPrice());
        }
        if (receivedGiftCertificate.getCreateDate() != null) {
            GiftCertificateValidator.validateDates(receivedGiftCertificate.getCreateDate(),
                    foundGiftCertificate.getLastUpdateDate());
            foundGiftCertificate.setCreateDate(receivedGiftCertificate.getCreateDate());
        }
        if (receivedGiftCertificate.getLastUpdateDate() != null) {
            GiftCertificateValidator.validateDates(foundGiftCertificate.getCreateDate(),
                    receivedGiftCertificate.getLastUpdateDate());
            foundGiftCertificate.setLastUpdateDate(receivedGiftCertificate.getLastUpdateDate());
        }
        return foundGiftCertificate;
    }
}
