package com.epam.esm.dao.impl;

import com.epam.esm.configuration.PersistenceConfiguration;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfiguration.class)
@Transactional
class OrderDaoImplTest {

    private final OrderDao orderDao;
    private static Order order1;
    private static Order order2;
    private static Order order3;
    private static Order order4;
    private static Order order5;
    private static Page page1;
    private static Page page2;
    private static Page page3;

    @Autowired
    public OrderDaoImplTest(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @BeforeAll
    static void setUp() {
        order1 = Order.builder()
                .createDate(LocalDateTime.now())
                .price(new BigDecimal("100"))
                .userId(1L)
                .giftCertificateId(2L)
                .build();
        order2 = Order.builder()
                .createDate(LocalDateTime.now())
                .price(new BigDecimal("100"))
                .userId(1L)
                .giftCertificateId(2L)
                .build();
        order3 = Order.builder()
                .id(1L)
                .createDate(LocalDateTime.now())
                .price(new BigDecimal("100"))
                .userId(1L)
                .giftCertificateId(2L)
                .build();
        order4 = Order.builder()
                .createDate(LocalDateTime.now())
                .price(new BigDecimal("100000000000"))
                .userId(1L)
                .giftCertificateId(2L)
                .build();
        order5 = Order.builder()
                .id(1L)
                .createDate(LocalDateTime.of(2019, 1, 1, 21, 0, 0))
                .price(new BigDecimal("1000.00"))
                .userId(1L)
                .giftCertificateId(2L)
                .build();
        page1 = Page.builder()
                .number(1)
                .size(5)
                .build();
        page2 = Page.builder()
                .number(3)
                .size(3)
                .build();
        page3 = Page.builder()
                .number(-3)
                .size(3)
                .build();
    }

    @AfterAll
    static void tearDown() {
        order1 = null;
        order2 = null;
        order3 = null;
        order4 = null;
        order5 = null;
        page1 = null;
        page2 = null;
        page3 = null;
    }

    @Test
    void addCorrectDataShouldReturnOrderTest() {
        // when
        Order actual = orderDao.add(order1);

        // then
        assertNotNull(actual);
    }

    @Test
    void addCorrectDataShouldSetIdTest() {
        // given
        long expected = 5;

        // when
        orderDao.add(order2);

        // then
        assertEquals(expected, order2.getId());
    }

    @Test
    void addCorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> orderDao.add(order3));
    }

    @Test
    void addIncorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(DataIntegrityViolationException.class, () -> orderDao.add(order4));
    }

    @Test
    void findAllShouldThrowExceptionTest() {
        // given
        Page page = new Page();

        // then
        assertThrows(UnsupportedOperationException.class, () -> orderDao.findAll(page));
    }

    @Test
    void findByIdCorrectDataShouldReturnOrderOptionalTest() {
        // given
        long id = 1;

        // when
        Optional<Order> actual = orderDao.findById(id);

        // then
        assertEquals(Optional.of(order5), actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnEmptyOptionalTest() {
        // given
        long id = 8;

        // when
        Optional<Order> actual = orderDao.findById(id);

        // then
        assertFalse(actual.isPresent());
    }

    @Test
    void updateShouldThrowExceptionTest() {
        // given
        Order order = new Order();

        // then
        assertThrows(UnsupportedOperationException.class, () -> orderDao.update(order));
    }

    @Test
    void removeShouldThrowExceptionTest() {
        // given
        long id = 1;

        // then
        assertThrows(UnsupportedOperationException.class, () -> orderDao.remove(id));
    }

    @Test
    void findByUserIdCorrectDataShouldReturnListOfOrdersTest() {
        // given
        long userId = 1;
        long expected = 2;

        // when
        List<Order> actual = orderDao.findByUserId(userId, page1);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findByUserIdCorrectDataShouldReturnEmptyListTest() {
        // given
        long userId = 1;

        // when
        List<Order> actual = orderDao.findByUserId(userId, page2);

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    void findByUserIdIncorrectDataShouldThrowExceptionTest() {
        // given
        long userId = 1;

        // then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> orderDao.findByUserId(userId, page3));
    }
}
