package com.epam.esm.dto;

import com.epam.esm.dto.group.AddGroup;
import com.epam.esm.dto.group.UpdateGroup;
import com.epam.esm.exception.ExceptionMessageKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Null;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class {@code GiftCertificateDto} is implementation of pattern DTO
 * for transmission {@link com.epam.esm.entity.GiftCertificate} between service and controller.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GiftCertificateDto extends RepresentationModel<TagDto> {

    @Null(message = ExceptionMessageKey.GIFT_CERTIFICATE_HAS_ID, groups = AddGroup.class)
    @Min(value = 1, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_ID, groups = UpdateGroup.class)
    private Long id;
    @NotBlank(message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_NAME)
    @Size(max = 100, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_NAME)
    private String name;
    @NotBlank(message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_DESCRIPTION)
    @Size(max = 1000, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_DESCRIPTION)
    private String description;
    @DecimalMin(value = "0.01",message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_PRICE)
    @Digits(integer = 6, fraction = 2, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_PRICE)
    private BigDecimal price;
    @Min(value = 1, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_DURATION)
    @Max(value = 1000, message = ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE_DURATION)
    private int duration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    private List<TagDto> tags;
}
