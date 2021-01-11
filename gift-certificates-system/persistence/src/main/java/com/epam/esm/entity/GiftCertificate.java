package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class {@code GiftCertificate} represents gift_certificate entity.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<Tag> tags;
}
