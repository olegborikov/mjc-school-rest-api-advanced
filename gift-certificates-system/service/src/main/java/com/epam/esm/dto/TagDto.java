package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionMessageKey;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * Class {@code TagDto} is implementation of pattern DTO
 * for transmission {@link com.epam.esm.entity.Tag} between service and controller.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagDto extends RepresentationModel<TagDto> {

    @Null(message = ExceptionMessageKey.TAG_HAS_ID)
    private Long id;
    @NotBlank(message = ExceptionMessageKey.INCORRECT_TAG_NAME)
    @Size(max = 100, message = ExceptionMessageKey.INCORRECT_TAG_NAME)
    private String name;
}
