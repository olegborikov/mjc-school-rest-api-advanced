package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.IncorrectParameterValueException;

import java.util.List;

public interface OrderService {

    OrderDto addOrder(OrderDto orderDto) throws IncorrectParameterValueException;
}
