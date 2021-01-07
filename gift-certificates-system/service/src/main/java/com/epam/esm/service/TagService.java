package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto addTag(TagDto tagDto);

    List<TagDto> findAllTags();

    TagDto findTagById(Long id);

    void removeTag(Long id);

    List<TagDto> findTagsByGiftCertificateId(Long id);
}
