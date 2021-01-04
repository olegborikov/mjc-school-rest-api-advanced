package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParametersException;
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
    private TagDao tagDao;
    private ModelMapper modelMapper;

    @Autowired
    @Override
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TagDto addTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        if (TagValidator.isNameCorrect(tag.getName())) {
            Tag addedTag = tagDao.add(tag);
            return modelMapper.map(addedTag, TagDto.class);
        } else {
            throw new IncorrectParametersException("Incorrect name value: " + tag.getName()
                    + ". Name should be string with length in range from 1 to 100 symbols.");
        }
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
        if (TagValidator.isIdCorrect(id)) {
            long parsedId = NumberUtils.createLong(id);
            Optional<Tag> foundTag = tagDao.findById(parsedId);
            return foundTag.map(tag -> modelMapper.map(tag, TagDto.class))
                    .orElseThrow(() -> new ResourceNotFoundException("Tag with id " + id + " not found."));
        } else {
            throw new IncorrectParametersException("Incorrect id value: " + id + ". Id should be positive number.");
        }
    }

    @Override
    public void removeTag(String id) {
        if (TagValidator.isIdCorrect(id)) {
            long parsedId = NumberUtils.createLong(id);
            boolean isRemoved = tagDao.remove(parsedId);
            if (!isRemoved) {
                throw new ResourceNotFoundException("Tag with id " + id + " not found.");
            }
        } else {
            throw new IncorrectParametersException("Incorrect id value: " + id + ". Id should be positive number.");
        }
    }
}
