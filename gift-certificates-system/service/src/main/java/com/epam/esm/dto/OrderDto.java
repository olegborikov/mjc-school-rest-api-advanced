package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends RepresentationModel<TagDto> {

    private Long id;
    private BigDecimal price;
    private LocalDateTime createDate;
    private Long userId;
    private Long giftCertificateId;
}
