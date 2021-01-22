package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable long id) {
        OrderDto foundOrderDto = orderService.findOrderById(id);
        addRelationship(foundOrderDto);
        return foundOrderDto;
    }

    @GetMapping("/users/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable long userId) {
        List<OrderDto> foundOrdersDto = orderService.findOrdersByUserId(userId);
        foundOrdersDto.forEach(this::addRelationship);
        return foundOrdersDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto addOrder(@RequestBody OrderDto orderDto) {
        OrderDto addedOrderDto = orderService.addOrder(orderDto);
        addRelationship(addedOrderDto);
        return addedOrderDto;
    }

    private void addRelationship(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel());
        orderDto.add(linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificateById(orderDto.getGiftCertificateId())).withRel("gift-certificate"));
        orderDto.add(linkTo(methodOn(UserController.class)
                .getUserById(orderDto.getUserId())).withRel("user"));
    }
}
