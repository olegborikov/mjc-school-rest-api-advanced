package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Interface {@code GiftCertificateService} describes abstract behavior for working
 * with {@link com.epam.esm.entity.GiftCertificate}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
public interface GiftCertificateService {

    /**
     * Add new gift certificate and records with gift certificate id and tags id to cross table.
     * If gift certificate has new tags they will be added.
     *
     * @param giftCertificateDto the gift certificate dto which will be added
     * @return the added gift certificate dto
     * @throws IncorrectParameterValueException an exception thrown by method
     *                                          {@link com.epam.esm.validator.GiftCertificateValidator#validate(GiftCertificate)}
     */
    GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto)
            throws IncorrectParameterValueException;

    /**
     * Find gift certificates according to parameters in {@link com.epam.esm.util.GiftCertificateQueryParameters}.
     *
     * @param giftCertificateQueryParametersDto the gift certificate query parameters dto
     *                                          according to which will be found gift certificates
     * @return the list of found gift certificates
     */
    List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParametersDto giftCertificateQueryParametersDto);

    /**
     * Find gift certificate by id.
     *
     * @param id the id of gift certificate which will be searched
     * @return the found gift certificate dto
     * @throws IncorrectParameterValueException an exception thrown by method
     *                                          {@link com.epam.esm.validator.GiftCertificateValidator#validateId(Long)}
     * @throws ResourceNotFoundException        an exception thrown in case gift certificate with such id not found
     */
    GiftCertificateDto findGiftCertificateById(Long id)
            throws IncorrectParameterValueException, ResourceNotFoundException;

    /**
     * Update gift certificate by fields.
     * If field value is null it will be not updated.
     * Gift certificate tags overwrite previous values.
     * If gift certificate has new tags they will be added.
     *
     * @param giftCertificateDto the gift certificate dto which will be updated
     * @return the updated gift certificate dto
     * @throws IncorrectParameterValueException an exception thrown by method
     *                                          {@link com.epam.esm.validator.GiftCertificateValidator#validate(GiftCertificate)}
     */
    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto)
            throws IncorrectParameterValueException;

    /**
     * Remove gift certificate and all recordings with such giftCertificateId in cross table.
     *
     * @param id the id of gift certificate which will be removed
     * @throws IncorrectParameterValueException an exception thrown by method
     *                                          {@link com.epam.esm.validator.GiftCertificateValidator#validateId(Long)}
     */
    void removeGiftCertificate(Long id) throws IncorrectParameterValueException;
}
