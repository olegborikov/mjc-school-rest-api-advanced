package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
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
    private final TagService tagService;
    private final ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      TagService tagService, ModelMapper modelMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto) {
        checkTags(giftCertificateDto);
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        GiftCertificateValidator.validateDuration(giftCertificate.getDuration());
        GiftCertificateValidator.validateName(giftCertificate.getName());
        GiftCertificateValidator.validateDescription(giftCertificate.getDescription());
        GiftCertificateValidator.validatePrice(giftCertificate.getPrice());
        GiftCertificateValidator.validateDates(giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate());
        GiftCertificate addedGiftCertificate = giftCertificateDao.add(giftCertificate);
        giftCertificateDao.addGiftCertificateHasTag(addedGiftCertificate);
        return modelMapper.map(addedGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParametersDto giftCertificateQueryParametersDto) {
        GiftCertificateQueryParameters giftCertificateQueryParameters
                = modelMapper.map(giftCertificateQueryParametersDto, GiftCertificateQueryParameters.class);
        List<GiftCertificate> giftCertificates
                = giftCertificateDao.findByQueryParameters(giftCertificateQueryParameters);
        return giftCertificates.stream()
                .map(giftCertificate -> {
                    GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
                    giftCertificateDto.setTags(tagService.findTagsByGiftCertificateId(giftCertificate.getId()));
                    return giftCertificateDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto findGiftCertificateById(Long id) {
        GiftCertificateValidator.validateId(id);
        Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);
        return foundGiftCertificate
                .map(giftCertificate -> {
                    GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
                    giftCertificateDto.setTags(tagService.findTagsByGiftCertificateId(giftCertificate.getId()));
                    return giftCertificateDto;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Gift certificate with id " + id + " not found."));
    }

    @Transactional
    @Override
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        checkTags(giftCertificateDto);
        GiftCertificate receivedGiftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        GiftCertificateValidator.validateId(receivedGiftCertificate.getId());
        Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(receivedGiftCertificate.getId());
        if (foundGiftCertificate.isPresent()) {
            GiftCertificate newGiftCertificate
                    = updateFieldsGiftCertificate(receivedGiftCertificate, foundGiftCertificate.get());
            GiftCertificate updatedGiftCertificate = giftCertificateDao.update(newGiftCertificate);
            giftCertificateDao.removeGiftCertificateHasTag(updatedGiftCertificate.getId());
            giftCertificateDao.addGiftCertificateHasTag(updatedGiftCertificate);
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

    private void checkTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = giftCertificateDto.getTags().stream()
                .map(tagService::addTag)
                .collect(Collectors.toList());
        giftCertificateDto.setTags(tags);
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
        if (receivedGiftCertificate.getTags() != null) {
            foundGiftCertificate.setTags(receivedGiftCertificate.getTags());
        }
        return foundGiftCertificate;
    }
}
