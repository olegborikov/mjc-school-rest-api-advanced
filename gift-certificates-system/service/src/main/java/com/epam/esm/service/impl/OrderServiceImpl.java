package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class {@code OrderServiceImpl} is implementation of interface {@link OrderService}
 * and intended to work with {@link com.epam.esm.entity.Order}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
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
    public OrderDto addOrder(OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        GiftCertificateDto foundGiftCertificateDto
                = giftCertificateService.findGiftCertificateById(orderDto.getGiftCertificateId());
        GiftCertificate foundGiftCertificate = modelMapper.map(foundGiftCertificateDto, GiftCertificate.class);
        userService.findUserById(orderDto.getUserId());
        order.setCreateDate(LocalDateTime.now());
        order.setPrice(foundGiftCertificate.getPrice());
        Order addedOrder = orderDao.add(order);
        return modelMapper.map(addedOrder, OrderDto.class);
    }

    @Override
    public OrderDto findOrderById(long id) throws ResourceNotFoundException {
        Optional<Order> foundOrderOptional = orderDao.findById(id);
        return foundOrderOptional
                .map(foundOrder -> modelMapper.map(foundOrder, OrderDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionMessageKey.ORDER_NOT_FOUND_BY_ID, String.valueOf(id)));
    }

    @Override
    public List<OrderDto> findOrdersByUserId(long userId, PageDto pageDto) {
        Page page = modelMapper.map(pageDto, Page.class);
        List<Order> foundOrders = orderDao.findByUserId(userId, page);
        return foundOrders.stream()
                .map(foundOrder -> modelMapper.map(foundOrder, OrderDto.class))
                .collect(Collectors.toList());
    }
}
