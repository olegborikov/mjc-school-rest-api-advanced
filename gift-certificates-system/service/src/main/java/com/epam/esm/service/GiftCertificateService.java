package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.group.OnCreate;
import com.epam.esm.dto.group.OnUpdate;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Interface {@code GiftCertificateService} describes abstract behavior for working
 * with {@link com.epam.esm.entity.GiftCertificate}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Validated
public interface GiftCertificateService {

    /**
     * Add new gift certificate and records with gift certificate id and tags id to cross table.
     * If gift certificate has new tags they will be added.
     *
     * @param giftCertificateDto the gift certificate dto which will be added
     * @return the added gift certificate dto
     */
    @Validated(OnCreate.class)
    GiftCertificateDto addGiftCertificate(@Valid @NotNull GiftCertificateDto giftCertificateDto);

    /**
     * Find gift certificates according to parameters in {@link com.epam.esm.util.GiftCertificateQueryParameters}.
     *
     * @param giftCertificateQueryParametersDto the gift certificate query parameters dto
     *                                          according to which will be found gift certificates
     * @param pageDto                           the pageDto containing information about pagination
     * @return the list of found gift certificates dto
     */
    List<GiftCertificateDto> findGiftCertificatesByQueryParameters(
            @NotNull GiftCertificateQueryParametersDto giftCertificateQueryParametersDto,
            @Valid @NotNull PageDto pageDto);

    /**
     * Find gift certificate by id.
     *
     * @param id the id of gift certificate which will be searched
     * @return the found gift certificate dto
     * @throws ResourceNotFoundException an exception thrown in case gift certificate with such id not found
     */
    GiftCertificateDto findGiftCertificateById(
            @Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_ID) long id)
            throws ResourceNotFoundException;

    /**
     * Update gift certificate by fields.
     * If field value is null it will be not updated.
     * Gift certificate tags overwrite previous values.
     * If gift certificate has new tags they will be added.
     *
     * @param giftCertificateDto the gift certificate dto which will be updated
     * @return the updated gift certificate dto
     */
    @Validated(OnUpdate.class)
    GiftCertificateDto updateGiftCertificate(
            @Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_ID,
                    groups = OnUpdate.class) long id,
            @Valid @NotNull GiftCertificateDto giftCertificateDto);

    /**
     * Remove gift certificate and all recordings with such giftCertificateId in cross table.
     *
     * @param id the id of gift certificate which will be removed
     * @throws ResourceNotFoundException an exception thrown by method
     *                                   {@link com.epam.esm.service.GiftCertificateService#findGiftCertificateById(long)}
     */
    void removeGiftCertificate(
            @Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_ID) long id)
            throws ResourceNotFoundException;
}
