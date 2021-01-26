package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
public interface OrderService {

    OrderDto addOrder(@Valid OrderDto orderDto);

    OrderDto findOrderById(@Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_ORDER_ID) long id)
            throws ResourceNotFoundException;

    List<OrderDto> findOrdersByUserId(
            @Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_USER_ID) long userId,
            @Valid PageDto pageDto);
}
