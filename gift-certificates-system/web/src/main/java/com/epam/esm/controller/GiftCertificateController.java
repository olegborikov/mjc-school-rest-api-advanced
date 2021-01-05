package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.IncorrectParametersException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.handler.ErrorHandler;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificateDto> giftCertificates() {
        return giftCertificateService.findAllGiftCertificates();
    }

    @GetMapping("/{id}")
    public GiftCertificateDto giftCertificateById(@PathVariable String id) {
        return giftCertificateService.findGiftCertificateById(id);
    }

    @PostMapping
    public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.addGiftCertificate(giftCertificateDto);
    }

    @PutMapping
    public GiftCertificateDto updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.updateGiftCertificate(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteGiftCertificate(@PathVariable String id) {
        giftCertificateService.removeGiftCertificate(id);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorHandler handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorHandler(exception.getMessage(), 44);
    }

    @ExceptionHandler(value = IncorrectParametersException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorHandler handleIncorrectParametersException(IncorrectParametersException exception) {
        return new ErrorHandler(exception.getMessage(), 40);
    }
}
