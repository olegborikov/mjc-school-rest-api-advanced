package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class {@code TagServiceImpl} is implementation of interface {@link TagService}
 * and intended to work with {@link com.epam.esm.entity.Tag}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final ModelMapper modelMapper;

    @Autowired
    public TagServiceImpl(TagDao tagDao, ModelMapper modelMapper) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public TagDto addTag(TagDto tagDto) throws IncorrectParameterValueException {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        TagValidator.validateName(tag.getName());
        Optional<Tag> existingTagOptional = tagDao.findByName(tag.getName());
        Tag addedTag = existingTagOptional.orElseGet(() -> tagDao.add(tag));
        return modelMapper.map(addedTag, TagDto.class);
    }

    @Override
    public List<TagDto> findAllTags() {
        List<Tag> foundTags = tagDao.findAll();
        return foundTags.stream()
                .map(foundTag -> modelMapper.map(foundTag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findTagById(Long id) throws IncorrectParameterValueException, ResourceNotFoundException {
        TagValidator.validateId(id);
        Optional<Tag> foundTagOptional = tagDao.findById(id);
        return foundTagOptional.map(foundTag -> modelMapper.map(foundTag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Tag with id " + id + " not found."));
    }

    @Transactional
    @Override
    public void removeTag(Long id) throws IncorrectParameterValueException {
        TagValidator.validateId(id);
        tagDao.removeGiftCertificateHasTag(id);
        tagDao.remove(id);
    }

    @Override
    public List<TagDto> findTagsByGiftCertificateId(Long giftCertificateId) throws IncorrectParameterValueException {
        GiftCertificateValidator.validateId(giftCertificateId);
        List<Tag> foundTags = tagDao.findByGiftCertificateId(giftCertificateId);
        return foundTags.stream()
                .map(foundTag -> modelMapper.map(foundTag, TagDto.class))
                .collect(Collectors.toList());
    }
}
