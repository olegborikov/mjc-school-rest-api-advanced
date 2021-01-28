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

/**
 * Interface {@code TagService} describes abstract behavior for working with {@link com.epam.esm.entity.Tag}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Validated
public interface TagService {

    /**
     * Add new tag.
     * If tag with such name already exists will be thrown exception.
     *
     * @param tagDto the tag dto which will be added
     * @return the added tag dto
     * @throws ResourceExistsException an exception thrown in case tag with such name already exists
     */
    TagDto addTag(@Valid @NotNull TagDto tagDto) throws ResourceExistsException;

    /**
     * Find all tags.
     *
     * @param pageDto the pageDto containing information about pagination
     * @return the list of all tags dto
     */
    List<TagDto> findAllTags(@Valid @NotNull PageDto pageDto);

    /**
     * Find tag by id.
     *
     * @param id the id of tag which will be searched
     * @return the found tag dto
     * @throws ResourceNotFoundException an exception thrown in case tag with such id not found
     */
    TagDto findTagById(@Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_TAG_ID) long id)
            throws ResourceNotFoundException;

    /**
     * Find tag by name.
     *
     * @param name the name of tag which will be searched
     * @return the found tag dto
     * @throws ResourceNotFoundException an exception thrown in case tag with such name not found
     */
    TagDto findTagByName(@Valid @NotBlank(message = ExceptionMessageKey.INCORRECT_TAG_NAME)
                         @Size(max = 100, message = ExceptionMessageKey.INCORRECT_TAG_NAME) String name)
            throws ResourceNotFoundException;

    /**
     * Check is tag with such name exists.
     *
     * @param name the name of tag which will be searched
     * @return the boolean of tag existing
     */
    boolean isExists(@Valid @NotBlank(message = ExceptionMessageKey.INCORRECT_TAG_NAME)
                     @Size(max = 100, message = ExceptionMessageKey.INCORRECT_TAG_NAME) String name);

    /**
     * Remove tag and all recordings with such tagId in cross table.
     *
     * @param id the id of tag which will be removed
     * @throws ResourceNotFoundException an exception thrown by method
     *                                   {@link com.epam.esm.service.TagService#findTagById(long)} (long)}
     */
    void removeTag(@Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_TAG_ID) long id)
            throws ResourceNotFoundException;

    /**
     * Find most popular tag of user with highest cost of all orders.
     *
     * @return the found tag dto
     * @throws ResourceNotFoundException an exception thrown in case tag not found
     */
    TagDto findMostPopularTagOfUserWithHighestCostOfAllOrders() throws ResourceNotFoundException;
}
