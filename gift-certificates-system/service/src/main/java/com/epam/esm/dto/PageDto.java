package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionMessageKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Class {@code PageDto} is implementation of pattern DTO
 * for transmission {@link com.epam.esm.util.Page} between service and controller.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {

    @Min(value = 1, message = ExceptionMessageKey.INCORRECT_PAGE_NUMBER)
    private int number;
    @Min(value = 1, message = ExceptionMessageKey.INCORRECT_PAGE_SIZE)
    @Max(value = 100, message = ExceptionMessageKey.INCORRECT_PAGE_SIZE)
    private int size;
}
