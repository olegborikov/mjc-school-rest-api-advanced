package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
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
    public TagDto addTag(TagDto tagDto) throws IncorrectParameterValueException, ResourceExistsException {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        if (tag.getId() != null) {
            throw new IncorrectParameterValueException(ExceptionMessageKey.TAG_HAS_ID, String.valueOf(tag));
        }
        if (isExists(tag.getName())) {
            throw new ResourceExistsException(ExceptionMessageKey.TAG_ALREADY_EXISTS, String.valueOf(tag.getName()));
        }
        Tag addedTag = tagDao.add(tag);
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
    public TagDto findTagById(long id) throws IncorrectParameterValueException, ResourceNotFoundException {
        TagValidator.validateId(id);
        Optional<Tag> foundTagOptional = tagDao.findById(id);
        return foundTagOptional.map(foundTag -> modelMapper.map(foundTag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionMessageKey.TAG_NOT_FOUND_BY_ID, String.valueOf(id)));
    }

    @Override
    public TagDto findTagByName(String name) throws IncorrectParameterValueException, ResourceNotFoundException {
        TagValidator.validateName(name);
        Optional<Tag> foundTagOptional = tagDao.findByName(name);
        return foundTagOptional.map(foundTag -> modelMapper.map(foundTag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionMessageKey.TAG_NOT_FOUND_BY_NAME, String.valueOf(name)));
    }

    @Override
    public boolean isExists(String name) throws IncorrectParameterValueException {
        TagValidator.validateName(name);
        Optional<Tag> existingTagOptional = tagDao.findByName(name);
        return existingTagOptional.isPresent();
    }

    @Transactional
    @Override
    public void removeTag(long id) throws IncorrectParameterValueException {
        findTagById(id);
        tagDao.removeGiftCertificateHasTag(id);
        tagDao.remove(id);
    }
}
