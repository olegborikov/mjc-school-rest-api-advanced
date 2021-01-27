package com.epam.esm.service.impl;

import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceConfiguration.class)
@Import(ValidationAutoConfiguration.class)
class OrderServiceImplTest {

    @MockBean
    private OrderDao orderDao;
    @MockBean
    private UserService userService;
    @MockBean
    private GiftCertificateService giftCertificateService;
    @Autowired
    private OrderService orderService;
    private static Order order1;
    private static Order order2;
    private static OrderDto orderDto1;
    private static OrderDto orderDto2;
    private static OrderDto orderDto3;
    private static OrderDto orderDto4;
    private static GiftCertificateDto giftCertificateDto1;
    private static UserDto userDto1;
    private static PageDto pageDto1;
    private static PageDto pageDto2;

    @BeforeAll
    static void setUp() {
        LocalDateTime localDateTime = LocalDateTime.now();
        order1 = Order.builder()
                .id(1L)
                .createDate(localDateTime)
                .price(new BigDecimal("100"))
                .userId(1L)
                .giftCertificateId(2L)
                .build();
        order2 = Order.builder()
                .id(1L)
                .createDate(localDateTime)
                .price(new BigDecimal("1000"))
                .userId(1L)
                .giftCertificateId(2L)
                .build();
        orderDto1 = OrderDto.builder()
                .id(1L)
                .createDate(localDateTime)
                .price(new BigDecimal("100"))
                .userId(1L)
                .giftCertificateId(2L)
                .build();
        orderDto2 = OrderDto.builder()
                .userId(1L)
                .giftCertificateId(2L)
                .build();
        orderDto3 = OrderDto.builder()
                .id(1L)
                .createDate(localDateTime)
                .price(new BigDecimal("1000"))
                .userId(1L)
                .giftCertificateId(2L)
                .build();
        orderDto4 = OrderDto.builder()
                .id(-1L)
                .userId(1L)
                .giftCertificateId(2L)
                .build();
        giftCertificateDto1 = GiftCertificateDto.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(1000))
                .duration(5)
                .createDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
                .lastUpdateDate(LocalDateTime.of(2020, 12, 13, 12, 0, 0))
                .build();
        userDto1 = UserDto.builder()
                .id(1L)
                .name("Oleg")
                .build();
        pageDto1 = PageDto.builder()
                .number(1)
                .size(5)
                .build();
        pageDto2 = PageDto.builder()
                .number(-3)
                .size(3)
                .build();
    }

    @AfterAll
    static void tearDown() {
        order1 = null;
        order2 = null;
        orderDto1 = null;
        orderDto2 = null;
        orderDto3 = null;
        orderDto4 = null;
        giftCertificateDto1 = null;
        userDto1 = null;
        pageDto1 = null;
        pageDto2 = null;
    }

    @Test
    void addOrderCorrectDataShouldReturnOrderDtoTest() {
        // given
        when(giftCertificateService.findGiftCertificateById(any(long.class))).thenReturn(giftCertificateDto1);
        when(userService.findUserById(any(long.class))).thenReturn(userDto1);
        when(orderDao.add(any(Order.class))).thenReturn(order2);

        // when
        OrderDto actual = orderService.addOrder(orderDto2);

        // then
        assertEquals(orderDto3, actual);
    }

    @Test
    void addOrderCorrectDataShouldThrowExceptionTest() {
        // given
        when(giftCertificateService.findGiftCertificateById(any(long.class))).thenReturn(giftCertificateDto1);
        when(userService.findUserById(any(long.class))).thenReturn(userDto1);
        when(orderDao.add(any(Order.class))).thenReturn(order2);

        // then
        assertThrows(ConstraintViolationException.class, () -> orderService.addOrder(orderDto4));
    }

    @Test
    void findOrderByIdCorrectDataShouldReturnOrderDtoTest() {
        // given
        long id = 1;
        when(orderDao.findById(any(long.class))).thenReturn(Optional.of(order1));

        // when
        OrderDto actual = orderService.findOrderById(id);

        // then
        assertEquals(orderDto1, actual);
    }

    @Test
    void findOrderByIdCorrectDataShouldThrowExceptionTest() {
        // given
        long id = 1;
        when(orderDao.findById(any(long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> orderService.findOrderById(id));
    }

    @Test
    void findOrderByIdIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = -1;
        when(orderDao.findById(any(long.class))).thenReturn(Optional.of(order1));

        // then
        assertThrows(ConstraintViolationException.class, () -> orderService.findOrderById(id));
    }

    @Test
    void findOrdersByUserIdCorrectDataShouldReturnListOfOrderDtoTest() {
        // given
        int expected = 2;
        long userId = 1;
        when(orderDao.findByUserId(any(long.class), any(Page.class))).thenReturn(Arrays.asList(order1, order2));

        // when
        List<OrderDto> actual = orderService.findOrdersByUserId(userId, pageDto1);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findOrdersByUserIdIncorrectDataShouldThrowExceptionTest() {
        // given
        long userId = 1;
        when(orderDao.findByUserId(any(long.class), any(Page.class))).thenReturn(Arrays.asList(order1, order2));

        // then
        assertThrows(ConstraintViolationException.class, () -> orderService.findOrdersByUserId(userId, pageDto2));
    }
}
