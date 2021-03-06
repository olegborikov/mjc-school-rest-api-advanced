package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ResourceExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
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
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public TagServiceImpl(TagDao tagDao, UserService userService, ModelMapper modelMapper) {
        this.tagDao = tagDao;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public TagDto addTag(TagDto tagDto) throws ResourceExistsException {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        if (isExists(tag.getName())) {
            throw new ResourceExistsException(ExceptionMessageKey.TAG_ALREADY_EXISTS, String.valueOf(tag.getName()));
        }
        Tag addedTag = tagDao.add(tag);
        return modelMapper.map(addedTag, TagDto.class);
    }

    @Override
    public List<TagDto> findAllTags(PageDto pageDto) {
        Page page = modelMapper.map(pageDto, Page.class);
        List<Tag> foundTags = tagDao.findAll(page);
        return foundTags.stream()
                .map(foundTag -> modelMapper.map(foundTag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findTagById(long id) throws ResourceNotFoundException {
        Optional<Tag> foundTagOptional = tagDao.findById(id);
        return foundTagOptional.map(foundTag -> modelMapper.map(foundTag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionMessageKey.TAG_NOT_FOUND_BY_ID, String.valueOf(id)));
    }

    @Override
    public TagDto findTagByName(String name) throws ResourceNotFoundException {
        Optional<Tag> foundTagOptional = tagDao.findByName(name);
        return foundTagOptional.map(foundTag -> modelMapper.map(foundTag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionMessageKey.TAG_NOT_FOUND_BY_NAME, String.valueOf(name)));
    }

    @Transactional
    @Override
    public boolean isExists(String name) {
        Optional<Tag> existingTagOptional = tagDao.findByName(name);
        return existingTagOptional.isPresent();
    }

    @Transactional
    @Override
    public void removeTag(long id) throws ResourceNotFoundException {
        findTagById(id);
        tagDao.removeGiftCertificateHasTag(id);
        tagDao.remove(id);
    }

    @Override
    public TagDto findMostPopularTagOfUserWithHighestCostOfAllOrders() throws ResourceNotFoundException {
        UserDto foundUserDto = userService.findUserByHighestCostOfAllOrders();
        User foundUser = modelMapper.map(foundUserDto, User.class);
        Optional<Tag> foundTagOptional = tagDao.findMostPopularOfUser(foundUser.getId());
        return foundTagOptional.map(foundTag -> modelMapper.map(foundTag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionMessageKey.MOST_POPULAR_TAG_OF_USER_NOT_FOUND, String.valueOf(foundUser.getId())));
    }
}
