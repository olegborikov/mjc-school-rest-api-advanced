package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface TagService {

    TagDto addTag(TagDto tagDto) throws IncorrectParameterValueException, ResourceExistsException;

    List<TagDto> findAllTags(@Valid PageDto pageDto);

    TagDto findTagById(long id) throws IncorrectParameterValueException, ResourceNotFoundException;

    TagDto findTagByName(String name) throws IncorrectParameterValueException, ResourceNotFoundException;

    boolean isExists(String name) throws IncorrectParameterValueException;

    void removeTag(long id) throws IncorrectParameterValueException;
}
