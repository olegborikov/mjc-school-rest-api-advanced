package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserService userService,
                            GiftCertificateService giftCertificateService, ModelMapper modelMapper) {
        this.orderDao = orderDao;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public OrderDto addOrder(OrderDto orderDto) throws IncorrectParameterValueException {
        if (orderDto.getGiftCertificateDto() == null) {
            throw new IncorrectParameterValueException(ExceptionMessageKey.INCORRECT_GIFT_CERTIFICATE,
                    String.valueOf(orderDto.getGiftCertificateDto()));
        }
        GiftCertificateDto giftCertificateDto
                = giftCertificateService.findGiftCertificateById(orderDto.getGiftCertificateDto().getId());
        orderDto.setGiftCertificateDto(giftCertificateDto);
        if (orderDto.getUserDto() == null) {
            throw new IncorrectParameterValueException(ExceptionMessageKey.INCORRECT_TAG,
                    String.valueOf(orderDto.getUserDto()));
        }
        UserDto userDto = userService.findUserById(orderDto.getUserDto().getId());
        orderDto.setUserDto(userDto);
        Order order = convertOrderDtoToOrder(orderDto);
        order.setCreateDate(LocalDateTime.now());
        order.setPrice(order.getGiftCertificate().getPrice());
        Order addedOrder = orderDao.add(order);
        return convertOrderToOrderDto(addedOrder);
    }

    private OrderDto convertOrderToOrderDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setUserDto(modelMapper.map(order.getUser(), UserDto.class));
        orderDto.setGiftCertificateDto(modelMapper.map(order.getGiftCertificate(), GiftCertificateDto.class));
        return orderDto;
    }

    private Order convertOrderDtoToOrder(OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        order.setUser(modelMapper.map(orderDto.getUserDto(), User.class));
        order.setGiftCertificate(modelMapper.map(orderDto.getGiftCertificateDto(), GiftCertificate.class));
        return order;
    }
}
