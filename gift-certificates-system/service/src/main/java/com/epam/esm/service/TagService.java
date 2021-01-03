package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    void setTagDao(TagDao tagDao);

    boolean addTag(Tag tag);

    List<Tag> findAllTags();

    Optional<Tag> findTagById(long id);

    boolean removeTag(long id);
}
