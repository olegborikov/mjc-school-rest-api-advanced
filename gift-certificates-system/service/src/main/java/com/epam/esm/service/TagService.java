package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Interface {@code TagService} describes abstract behavior for working with {@link com.epam.esm.entity.Tag}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
public interface TagService {

    /**
     * Add new tag.
     * If tag with such name already exists it will be returns.
     *
     * @param tagDto the tag dto which will be added
     * @return the added tag dto
     * @throws IncorrectParameterValueException an exception thrown by method
     *                                          {@link com.epam.esm.validator.TagValidator#validateName(String)}
     */
    TagDto addTag(TagDto tagDto) throws IncorrectParameterValueException;

    /**
     * Find all tags.
     *
     * @return the list of all tags dto
     */
    List<TagDto> findAllTags();

    /**
     * Find tag by id.
     *
     * @param id the id of tag which will be searched
     * @return the found tag dto
     * @throws IncorrectParameterValueException an exception thrown by method
     *                                          {@link com.epam.esm.validator.TagValidator#validateId(long)}
     * @throws ResourceNotFoundException        an exception thrown in case tag with such id not found
     */
    TagDto findTagById(long id) throws IncorrectParameterValueException, ResourceNotFoundException;

    /**
     * Remove tag and all recordings with such tagId in cross table.
     *
     * @param id the id of tag which will be removed
     * @throws IncorrectParameterValueException an exception thrown by method
     *                                          {@link com.epam.esm.validator.TagValidator#validateId(long)}
     */
    void removeTag(long id) throws IncorrectParameterValueException;

    /**
     * Find tags which contains gift certificate.
     *
     * @param giftCertificateId the id of gift certificate which tags will be searched
     * @return the list of found tags dto
     * @throws IncorrectParameterValueException an exception thrown by method
     *                                          {@link com.epam.esm.validator.GiftCertificateValidator#validateId(long)}
     */
    List<TagDto> findTagsByGiftCertificateId(long giftCertificateId) throws IncorrectParameterValueException;
}
