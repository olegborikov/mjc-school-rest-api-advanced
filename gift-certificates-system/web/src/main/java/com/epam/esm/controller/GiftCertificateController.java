package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * Class {@code GiftCertificateController} is an endpoint of the API
 * which allows to perform CRUD operations on gift certificates.
 * Annotated by {@link RestController} with no parameters
 * to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/gift-certificates". So that
 * {@code GiftCertificateController} is accessed by sending request to /gift-certificates.
 *
 * @author Oleg Borikov
 * @since 1.0
 */
@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Get all gift certificates.
     * Annotated by {@link GetMapping} with no parameters.
     * Therefore, processes GET requests at /gift-certificates.
     * Can be set {@link GiftCertificateQueryParametersDto} following which the selection will be made.
     *
     * @param giftCertificateQueryParametersDto the gift certificate query parameters
     * @return the list of found gift certificates dto
     */
    @GetMapping
    public List<GiftCertificateDto> getGiftCertificates(
            GiftCertificateQueryParametersDto giftCertificateQueryParametersDto) {
        return giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto);
    }

    /**
     * Get gift certificate by id.
     * Annotated by {@link GetMapping} with parameter value = "/{id}".
     * Therefore, processes GET requests at /gift-certificates/{id}.
     *
     * @param id the gift certificate id, which will be found. Inferred from the request URI
     * @return the found gift certificate dto
     */
    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable Long id) {
        return giftCertificateService.findGiftCertificateById(id);
    }

    /**
     * Add new gift certificate.
     * Annotated by {@link PostMapping} with no parameters.
     * Therefore, processes POST requests at /gift-certificates.
     *
     * @param giftCertificateDto the new gift certificate, which will be added
     * @return the added gift certificate dto
     */
    @PostMapping
    public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.addGiftCertificate(giftCertificateDto);
    }

    /**
     * Update gift certificate.
     * Annotated by {@link PutMapping} with parameter value = "/{id}".
     * Therefore, processes PUT requests at /gift-certificates/{id}.
     *
     * @param id                 the gift certificate id, which will be updated. Inferred from the request URI
     * @param giftCertificateDto the gift certificate with updated fields
     * @return the updated gift certificate dto
     */
    @PutMapping("/{id}")
    public GiftCertificateDto updateGiftCertificate(@PathVariable Long id,
                                                    @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        return giftCertificateService.updateGiftCertificate(giftCertificateDto);
    }

    /**
     * Delete gift certificate with the specified id.
     * Annotated by {@link DeleteMapping} with parameter value = "/{id}".
     * Therefore, processes DELETE requests at /gift-certificates/{id}.
     *
     * @param id the gift certificate id, which will be deleted. Inferred from the request URI
     */
    @DeleteMapping("/{id}")
    public void deleteGiftCertificate(@PathVariable Long id) {
        giftCertificateService.removeGiftCertificate(id);
    }
}
