package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code GiftCertificateController} is an endpoint of the API
 * which allows to perform CRUD operations on gift certificates.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/gift-certificates".
 * So that {@code GiftCertificateController} is accessed by sending request to /gift-certificates.
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
     * Get all gift certificates by query parameters.
     * Annotated by {@link GetMapping} with no parameters.
     * Therefore, processes GET requests at /gift-certificates.
     *
     * @param tagName     the tag name query parameter
     * @param name        the name query parameter
     * @param description the description query parameter
     * @param sortType    the sort type query parameter
     * @param orderType   the order type query parameter
     * @param page        the number of page for pagination
     * @param size        the size of page for pagination
     * @return the list of found gift certificates dto
     */
    @GetMapping
    public List<GiftCertificateDto> getGiftCertificatesByQueryParameters(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) GiftCertificateQueryParametersDto.SortType sortType,
            @RequestParam(required = false) GiftCertificateQueryParametersDto.OrderType orderType,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int size) {
        GiftCertificateQueryParametersDto giftCertificateQueryParametersDto
                = new GiftCertificateQueryParametersDto(tagName, name, description, sortType, orderType);
        PageDto pageDto = new PageDto(page, size);
        List<GiftCertificateDto> foundGiftCertificatesDto
                = giftCertificateService.findGiftCertificatesByQueryParameters(giftCertificateQueryParametersDto, pageDto);
        foundGiftCertificatesDto.forEach(this::addRelationship);
        return foundGiftCertificatesDto;
    }

    /**
     * Get gift certificate by id.
     * Annotated by {@link GetMapping} with parameter value = "/{id}".
     * Therefore, processes GET requests at /gift-certificates/{id}.
     *
     * @param id the gift certificate id which will be found. Inferred from the request URI
     * @return the found gift certificate dto
     */
    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable long id) {
        GiftCertificateDto foundGiftCertificateDto = giftCertificateService.findGiftCertificateById(id);
        addRelationship(foundGiftCertificateDto);
        return foundGiftCertificateDto;
    }

    /**
     * Add new gift certificate.
     * Annotated by {@link PostMapping} with no parameters.
     * Therefore, processes POST requests at /gift-certificates.
     *
     * @param giftCertificateDto the new gift certificate which will be added
     * @return the added gift certificate dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto addedGiftCertificateDto = giftCertificateService.addGiftCertificate(giftCertificateDto);
        addRelationship(addedGiftCertificateDto);
        return addedGiftCertificateDto;
    }

    /**
     * Update gift certificate.
     * Annotated by {@link PutMapping} with parameter value = "/{id}".
     * Therefore, processes PUT requests at /gift-certificates/{id}.
     *
     * @param id                 the gift certificate id which will be updated. Inferred from the request URI
     * @param giftCertificateDto the gift certificate with updated fields
     * @return the updated gift certificate dto
     */
    @PutMapping("/{id}")
    public GiftCertificateDto updateGiftCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto updatedGiftCertificateDto
                = giftCertificateService.updateGiftCertificate(id, giftCertificateDto);
        addRelationship(updatedGiftCertificateDto);
        return updatedGiftCertificateDto;
    }

    /**
     * Delete gift certificate with the specified id.
     * Annotated by {@link DeleteMapping} with parameter value = "/{id}".
     * Therefore, processes DELETE requests at /gift-certificates/{id}.
     *
     * @param id the gift certificate id which will be deleted. Inferred from the request URI
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable long id) {
        giftCertificateService.removeGiftCertificate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void addRelationship(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificateById(giftCertificateDto.getId())).withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                .updateGiftCertificate(giftCertificateDto.getId(), giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                .deleteGiftCertificate(giftCertificateDto.getId())).withRel("delete"));
        giftCertificateDto.getTags().forEach(
                tagDto -> tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel()));
    }
}
