package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.util.Page;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.PageValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class {@code GiftCertificateServiceImpl} is implementation of interface {@link GiftCertificateService}
 * and intended to work with {@link com.epam.esm.entity.GiftCertificate}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
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
    public GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto)
            throws IncorrectParameterValueException {
        addAndSetTags(giftCertificateDto);
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        if (giftCertificate.getId() != null) {
            throw new IncorrectParameterValueException(ExceptionMessageKey.GIFT_CERTIFICATE_HAS_ID,
                    String.valueOf(giftCertificate));
        }
        GiftCertificateValidator.validate(giftCertificate);
        LocalDateTime currentTime = LocalDateTime.now();
        giftCertificate.setCreateDate(currentTime);
        giftCertificate.setLastUpdateDate(currentTime);
        GiftCertificate addedGiftCertificate = giftCertificateDao.add(giftCertificate);
        return modelMapper.map(addedGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParametersDto giftCertificateQueryParametersDto, PageDto pageDto) {
        Page page = modelMapper.map(pageDto, Page.class);
        GiftCertificateQueryParameters giftCertificateQueryParameters
                = modelMapper.map(giftCertificateQueryParametersDto, GiftCertificateQueryParameters.class);
        PageValidator.validate(page);
        List<GiftCertificate> foundGiftCertificates
                = giftCertificateDao.findByQueryParameters(giftCertificateQueryParameters, page);
        return foundGiftCertificates.stream()
                .map(foundGiftCertificate -> modelMapper.map(foundGiftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto findGiftCertificateById(long id)
            throws IncorrectParameterValueException, ResourceNotFoundException {
        GiftCertificateValidator.validateId(id);
        Optional<GiftCertificate> foundGiftCertificateOptional = giftCertificateDao.findById(id);
        return foundGiftCertificateOptional
                .map(foundGiftCertificate -> modelMapper.map(foundGiftCertificate, GiftCertificateDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND_BY_ID, String.valueOf(id)));
    }

    @Transactional
    @Override
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto)
            throws IncorrectParameterValueException {
        addAndSetTags(giftCertificateDto);
        GiftCertificateDto foundGiftCertificateDto = findGiftCertificateById(giftCertificateDto.getId());
        updateFields(foundGiftCertificateDto, giftCertificateDto);
        GiftCertificate foundGiftCertificate = modelMapper.map(foundGiftCertificateDto, GiftCertificate.class);
        GiftCertificateValidator.validate(foundGiftCertificate);
        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(foundGiftCertificate);
        return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
    }

    @Transactional
    @Override
    public void removeGiftCertificate(long id) throws IncorrectParameterValueException {
        findGiftCertificateById(id);
        giftCertificateDao.removeGiftCertificateHasTag(id);
        giftCertificateDao.remove(id);
    }

    private void addAndSetTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = new ArrayList<>();
        if (giftCertificateDto.getTags() != null) {
            Set<TagDto> tagsSet = giftCertificateDto.getTags().stream()
                    .map(tag -> tagService.isExists(tag.getName())
                            ? tagService.findTagByName(tag.getName())
                            : tagService.addTag(tag))
                    .collect(Collectors.toSet());
            tags.addAll(tagsSet);
        }
        giftCertificateDto.setTags(tags);
    }

    private void updateFields(GiftCertificateDto foundGiftCertificate, GiftCertificateDto receivedGiftCertificate) {
        if (receivedGiftCertificate.getDuration() != 0) {
            foundGiftCertificate.setDuration(receivedGiftCertificate.getDuration());
        }
        if (receivedGiftCertificate.getName() != null) {
            foundGiftCertificate.setName(receivedGiftCertificate.getName());
        }
        if (receivedGiftCertificate.getDescription() != null) {
            foundGiftCertificate.setDescription(receivedGiftCertificate.getDescription());
        }
        if (receivedGiftCertificate.getPrice() != null) {
            foundGiftCertificate.setPrice(receivedGiftCertificate.getPrice());
        }
        foundGiftCertificate.setLastUpdateDate(LocalDateTime.now());
        foundGiftCertificate.setTags(receivedGiftCertificate.getTags());
    }
}
