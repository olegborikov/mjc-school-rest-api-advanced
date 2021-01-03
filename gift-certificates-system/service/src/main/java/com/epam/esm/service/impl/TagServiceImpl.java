package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private TagDao tagDao;

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public boolean addTag(Tag tag) {
        boolean isAdded = false;
        if (tag.getId() > 0 && TagValidator.isNameCorrect(tag.getName())) {
            isAdded = tagDao.add(tag);
        }
        return isAdded;
    }

    public List<Tag> findAllTags() {
        return tagDao.findAll();
    }

    public Optional<Tag> findTagById(long id) {
        Optional<Tag> foundTag = Optional.empty();
        if (id > 0) {
            foundTag = tagDao.findById(id);
        }
        return foundTag;
    }

    public boolean removeTag(long id) {
        boolean isRemoved = false;
        if (id > 0) {
            isRemoved = tagDao.remove(id);
        }
        return isRemoved;
    }
}
