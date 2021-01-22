package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificateDto> getGiftCertificates(
            GiftCertificateQueryParametersDto giftCertificateQueryParametersDto) {
        List<GiftCertificateDto> foundGiftCertificatesDto
                = giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto);
        foundGiftCertificatesDto.forEach(this::addRelationship);
        return foundGiftCertificatesDto;
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable long id) {
        GiftCertificateDto foundGiftCertificateDto = giftCertificateService.findGiftCertificateById(id);
        addRelationship(foundGiftCertificateDto);
        return foundGiftCertificateDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto addedGiftCertificateDto = giftCertificateService.addGiftCertificate(giftCertificateDto);
        addRelationship(addedGiftCertificateDto);
        return addedGiftCertificateDto;
    }

    @PutMapping("/{id}")
    public GiftCertificateDto updateGiftCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        GiftCertificateDto updatedGiftCertificateDto
                = giftCertificateService.updateGiftCertificate(giftCertificateDto);
        addRelationship(updatedGiftCertificateDto);
        return updatedGiftCertificateDto;
    }

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
        giftCertificateDto.getTags().forEach(tagDto
                -> tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel()));
    }
}
