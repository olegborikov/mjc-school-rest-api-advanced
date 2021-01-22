package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends RepresentationModel<TagDto> {

    private Long id;
    private BigDecimal price;
    private LocalDateTime createDate;
    private UserDto userDto;
    private GiftCertificateDto giftCertificateDto;
}
