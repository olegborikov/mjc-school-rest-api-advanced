package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Long id;
    private String name;
    private List<GiftCertificate> giftCertificates;
}
