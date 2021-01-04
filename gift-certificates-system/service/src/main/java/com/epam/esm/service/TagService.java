package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    void setTagDao(TagDao tagDao);

    TagDto addTag(TagDto tagDto);

    List<TagDto> findAllTags();

    TagDto findTagById(long id);

    void removeTag(long id);
}
