package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ResourceExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
public interface TagService {

    TagDto addTag(@Valid @NotNull TagDto tagDto) throws ResourceExistsException;

    List<TagDto> findAllTags(@Valid @NotNull PageDto pageDto);

    TagDto findTagById(@Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_TAG_ID) long id)
            throws ResourceNotFoundException;

    TagDto findTagByName(@Valid @NotBlank(message = ExceptionMessageKey.INCORRECT_TAG_NAME)
                         @Size(max = 100, message = ExceptionMessageKey.INCORRECT_TAG_NAME) String name)
            throws ResourceNotFoundException;

    boolean isExists(@Valid @NotBlank(message = ExceptionMessageKey.INCORRECT_TAG_NAME)
                     @Size(max = 100, message = ExceptionMessageKey.INCORRECT_TAG_NAME) String name);

    void removeTag(@Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_TAG_ID) long id);

    TagDto findMostPopularTagOfUserWithHighestCostOfAllOrders() throws ResourceNotFoundException;
}
