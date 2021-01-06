package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.IncorrectParametersValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.validator.GiftCertificateValidator;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, ModelMapper modelMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        if (giftCertificate.getDuration() > 0
                && GiftCertificateValidator.isNameCorrect(giftCertificate.getName())
                && GiftCertificateValidator.isDescriptionCorrect(giftCertificate.getDescription())
                && GiftCertificateValidator.isPriceCorrect(giftCertificate.getPrice())
                && giftCertificate.getCreateDate().isBefore(giftCertificate.getLastUpdateDate())) {
            GiftCertificate addedGiftCertificate = giftCertificateDao.add(giftCertificate);
            return modelMapper.map(addedGiftCertificate, GiftCertificateDto.class);
        } else {
            StringBuilder message = new StringBuilder("Incorrect gift certificate parameters.");
            message.append(" Duration: ").append(giftCertificateDto.getDuration());
            message.append(", duration should be positive number.");
            message.append(" Name: ").append(giftCertificateDto.getName());
            message.append(", name should be string with length in range from 1 to 100 symbols.");
            message.append(" Description: ").append(giftCertificateDto.getDescription());
            message.append(", description should be string with length in range from 1 to 1000 symbols.");
            message.append(" Price: ").append(giftCertificateDto.getPrice());
            message.append(", price should be positive number before 100000000 and have two numbers in scale.");
            message.append("Create date should be before last update date.");
            throw new IncorrectParametersValueException(message.toString());
        }
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParametersDto giftCertificateQueryParametersDto) {
        GiftCertificateQueryParameters giftCertificateQueryParameters
                = modelMapper.map(giftCertificateQueryParametersDto, GiftCertificateQueryParameters.class);
        List<GiftCertificate> giftCertificates
                = giftCertificateDao.findByQueryParameters(giftCertificateQueryParameters);
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto findGiftCertificateById(String id) {
        if (GiftCertificateValidator.isIdCorrect(id)) {
            long parsedId = NumberUtils.createLong(id);
            Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(parsedId);
            return foundGiftCertificate
                    .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                    .orElseThrow(() -> new ResourceNotFoundException("Gift certificate with id " + id + "not found."));
        } else {
            throw new IncorrectParametersValueException("Incorrect id value: "
                    + id + ". Id should be positive number.");
        }
    }

    @Override
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate receivedGiftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        if (receivedGiftCertificate.getId() != null && receivedGiftCertificate.getId() > 0) {
            Optional<GiftCertificate> foundGiftCertificate
                    = giftCertificateDao.findById(receivedGiftCertificate.getId());
            if (foundGiftCertificate.isPresent()) {
                GiftCertificate updatedGiftCertificate
                        = updateFieldsGiftCertificate(receivedGiftCertificate, foundGiftCertificate.get());
                boolean isUpdated = giftCertificateDao.update(updatedGiftCertificate);
                if (isUpdated) {
                    return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
                } else {
                    throw new ResourceNotFoundException("Gift certificate with id "
                            + receivedGiftCertificate.getId() + " not found.");
                }
            } else {
                throw new ResourceNotFoundException("Gift certificate with id "
                        + receivedGiftCertificate.getId() + " not found.");
            }
        } else {
            throw new IncorrectParametersValueException("Incorrect id value: "
                    + receivedGiftCertificate.getId() + ". Id should be positive number."); // TODO: 06.01.2021 test
        }
    }

    @Override
    public void removeGiftCertificate(String id) {
        if (GiftCertificateValidator.isIdCorrect(id)) {
            long parsedId = NumberUtils.createLong(id);
            boolean isRemoved = giftCertificateDao.remove(parsedId);
            if (!isRemoved) {
                throw new ResourceNotFoundException("Gift certificate with id " + id + " not found.");
            }
        } else {
            throw new IncorrectParametersValueException("Incorrect id value: "
                    + id + ". Id should be positive number.");
        }
    }

    private GiftCertificate updateFieldsGiftCertificate(GiftCertificate receivedGiftCertificate,
                                                        GiftCertificate foundGiftCertificate) {
        if (receivedGiftCertificate.getDuration() > 0) {
            foundGiftCertificate.setDuration(receivedGiftCertificate.getDuration());
        }
        if (GiftCertificateValidator.isNameCorrect(receivedGiftCertificate.getName())) {
            foundGiftCertificate.setName(receivedGiftCertificate.getName());
        }
        if (GiftCertificateValidator.isDescriptionCorrect(receivedGiftCertificate.getDescription())) {
            foundGiftCertificate.setDescription(receivedGiftCertificate.getDescription());
        }
        if (GiftCertificateValidator.isPriceCorrect(receivedGiftCertificate.getPrice())) {
            foundGiftCertificate.setPrice(receivedGiftCertificate.getPrice());
        }
        if (receivedGiftCertificate.getCreateDate() != null &&
                receivedGiftCertificate.getCreateDate().isBefore(foundGiftCertificate.getLastUpdateDate())) {
            foundGiftCertificate.setCreateDate(receivedGiftCertificate.getCreateDate());
        }
        if (receivedGiftCertificate.getLastUpdateDate() != null &&
                foundGiftCertificate.getLastUpdateDate().isBefore(receivedGiftCertificate.getLastUpdateDate())) {
            foundGiftCertificate.setLastUpdateDate(receivedGiftCertificate.getLastUpdateDate());
        }
        return foundGiftCertificate;
    }
}
