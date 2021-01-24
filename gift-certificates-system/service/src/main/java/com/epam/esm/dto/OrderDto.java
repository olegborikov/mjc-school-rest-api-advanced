package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionMessageKey;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends RepresentationModel<TagDto> {

    @Null(message = ExceptionMessageKey.ORDER_HAS_ID)
    private Long id;
    private BigDecimal price;
    private LocalDateTime createDate;
    @NotNull(message = ExceptionMessageKey.INCORRECT_USER_ID)
    @Min(value = 1, message = ExceptionMessageKey.INCORRECT_USER_ID)
    private Long userId;
    @NotNull(message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_ID)
    @Min(value = 1, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_ID)
    private Long giftCertificateId;
}
