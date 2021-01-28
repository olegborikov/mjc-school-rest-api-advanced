package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code OrderController} is an endpoint of the API which allows to perform operations on orders.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/orders".
 * So that {@code OrderController} is accessed by sending request to /orders.
 *
 * @author Oleg Borikov
 * @since 1.0
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Get order by id.
     * Annotated by {@link GetMapping} with parameter value = "/{id}".
     * Therefore, processes GET requests at /orders/{id}.
     *
     * @param id the order id which will be found. Inferred from the request URI
     * @return the found order dto
     */
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable long id) {
        OrderDto foundOrderDto = orderService.findOrderById(id);
        addRelationship(foundOrderDto);
        return foundOrderDto;
    }

    /**
     * Get order by user id.
     * Annotated by {@link GetMapping} with parameter value = "/users/{userId}".
     * Therefore, processes GET requests at /orders/users/{userId}.
     *
     * @param userId the user id which orders will be found. Inferred from the request URI
     * @param page   the number of page for pagination
     * @param size   the size of page for pagination
     * @return the list of all user's orders dto
     */
    @GetMapping("/users/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable long userId,
                                            @RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(page, size);
        List<OrderDto> foundOrdersDto = orderService.findOrdersByUserId(userId, pageDto);
        foundOrdersDto.forEach(this::addRelationship);
        return foundOrdersDto;
    }

    /**
     * Add new order.
     * Annotated by {@link PostMapping} with no parameters.
     * Therefore, processes POST requests at /orders.
     *
     * @param orderDto the new order which will be added
     * @return the added order dto
     */
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
