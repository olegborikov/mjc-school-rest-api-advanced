package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
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

    public TagDto addTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        if (TagValidator.isNameCorrect(tag.getName())) {
            Tag addedTag = tagDao.add(tag);
            return modelMapper.map(addedTag, TagDto.class);
        } else {
            return null; // TODO: 04.01.2021
        }
    }

    public List<TagDto> findAllTags() {
        List<Tag> tags = tagDao.findAll();
        return tags.stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    public TagDto findTagById(long id) {
        if (id > 0) {
            Optional<Tag> foundTag = tagDao.findById(id);
            return foundTag.map(tag -> modelMapper.map(tag, TagDto.class))
                    .orElse(null);  // TODO: 04.01.2021
        } else {
            return null; // TODO: 04.01.2021
        }
    }

    public void removeTag(long id) {
        if (id > 0) {
            boolean isRemoved = tagDao.remove(id);
            if (!isRemoved) {
                // TODO: 04.01.2021
            }
        } else {
            // TODO: 04.01.2021
        }
    }
}
