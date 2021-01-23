package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "gift_certificate_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(precision = 8, scale = 2)
    private BigDecimal price;
    private LocalDateTime createDate;
    private Long userId;
    private Long giftCertificateId;
    @ManyToOne
    @Transient
    private User user;
    @ManyToOne
    @Transient
    private GiftCertificate giftCertificate;
}
