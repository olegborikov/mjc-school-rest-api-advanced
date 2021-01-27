package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Class {@code Order} represents gift_certificate_order entity.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Entity
@Table(name = "gift_certificate_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_certificate_order_id")
    private Long id;
    @Column(name = "gift_certificate_order_price")
    private BigDecimal price;
    @Column(name = "gift_certificate_order_create_date")
    private LocalDateTime createDate;
    @Column(name = "user_id_fk")
    private Long userId;
    @Column(name = "gift_certificate_id_fk")
    private Long giftCertificateId;
}
