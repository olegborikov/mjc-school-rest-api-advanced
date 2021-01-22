package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

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
public class TagDto extends RepresentationModel<TagDto> {

    private Long id;
    private String name;
}
