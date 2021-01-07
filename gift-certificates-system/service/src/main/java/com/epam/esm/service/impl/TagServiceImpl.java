package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
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
    public TagDto addTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        TagValidator.validateName(tag.getName());
        Optional<Tag> existingTag = tagDao.isExists(tag.getName());
        Tag addedTag = existingTag.orElseGet(() -> tagDao.add(tag));
        return modelMapper.map(addedTag, TagDto.class);
    }

    @Override
    public List<TagDto> findAllTags() {
        List<Tag> tags = tagDao.findAll();
        return tags.stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findTagById(Long id) {
        TagValidator.validateId(id);
        Optional<Tag> foundTag = tagDao.findById(id);
        return foundTag.map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Tag with id " + id + " not found."));
    }

    @Transactional
    @Override
    public void removeTag(Long id) {
        TagValidator.validateId(id);
        tagDao.removeGiftCertificateHasTag(id);
        tagDao.remove(id);
    }

    @Override
    public List<TagDto> findTagsByGiftCertificateId(Long id) {
        GiftCertificateValidator.validateId(id);
        List<Tag> tags = tagDao.findByGiftCertificateId(id);
        return tags.stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }
}
