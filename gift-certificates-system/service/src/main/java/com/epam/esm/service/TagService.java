package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto addTag(TagDto tagDto);

    List<TagDto> findAllTags();

    TagDto findTagById(String id);

    void removeTag(String id);
}
