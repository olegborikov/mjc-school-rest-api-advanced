package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public TagDto addTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        TagValidator.validateName(tag.getName());
        Tag addedTag = tagDao.add(tag);
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
    public TagDto findTagById(String id) {
        TagValidator.validateId(id);
        long parsedId = NumberUtils.createLong(id);
        Optional<Tag> foundTag = tagDao.findById(parsedId);
        return foundTag.map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Tag with id " + id + " not found."));
    }

    @Override
    public void removeTag(String id) {
        TagValidator.validateId(id);
        long parsedId = NumberUtils.createLong(id);
        boolean isRemoved = tagDao.remove(parsedId);
        if (!isRemoved) {
            throw new ResourceNotFoundException("Tag with id " + id + " not found.");
        }
    }
}
