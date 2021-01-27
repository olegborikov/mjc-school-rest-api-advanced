package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Interface {@code OrderService} describes abstract behavior for working with {@link com.epam.esm.entity.Order}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Validated
public interface OrderService {

    /**
     * Add new order.
     *
     * @return the added order dto
     */
    OrderDto addOrder(@Valid @NotNull OrderDto orderDto);

    /**
     * Find order by id.
     *
     * @param id the id of order which will be searched
     * @return the found order dto
     * @throws ResourceNotFoundException an exception thrown in case order with such id not found
     */
    OrderDto findOrderById(@Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_ORDER_ID) long id)
            throws ResourceNotFoundException;

    /**
     * Find orders by user id.
     *
     * @param userId  the id of user which orders will be searched
     * @param pageDto the pageDto containing information about pagination
     * @return the list of found orders dto
     */
    List<OrderDto> findOrdersByUserId(
            @Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_USER_ID) long userId,
            @Valid @NotNull PageDto pageDto);
}
