package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class {@code GiftCertificate} represents gift_certificate entity.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Entity
@Table(name = "gift_certificate")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_certificate_id")
    private Long id;
    @Column(name = "gift_certificate_name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "gift_certificate_price")
    private BigDecimal price;
    @Column(name = "duration")
    private int duration;
    @Column(name = "gift_certificate_create_date")
    private LocalDateTime createDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @ManyToMany
    @JoinTable(
            name = "gift_certificate_has_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id_fk"),
            inverseJoinColumns = @JoinColumn(name = "tag_id_fk")
    )
    private List<Tag> tags;
}
