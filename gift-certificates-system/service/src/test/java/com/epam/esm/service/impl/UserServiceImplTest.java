package com.epam.esm.service.impl;

import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
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
class UserServiceImplTest {

    @MockBean
    private UserDao userDao;
    @Autowired
    private UserService userService;
    private static User user1;
    private static User user2;
    private static UserDto userDto1;
    private static PageDto pageDto1;
    private static PageDto pageDto2;

    @BeforeAll
    static void setUp() {
        user1 = User.builder()
                .id(1L)
                .name("Oleg")
                .build();
        user2 = User.builder()
                .id(2L)
                .name("Ivan")
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
        user1 = null;
        user2 = null;
        userDto1 = null;
        pageDto1 = null;
        pageDto2 = null;
    }

    @Test
    void findAllUsersCorrectDataShouldReturnListOfUserDtoTest() {
        // given
        int expected = 2;
        when(userDao.findAll(any(Page.class))).thenReturn(Arrays.asList(user1, user2));

        // when
        List<UserDto> actual = userService.findAllUsers(pageDto1);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findAllTagsIncorrectDataShouldThrowExceptionTest() {
        // given
        when(userDao.findAll(any(Page.class))).thenReturn(Arrays.asList(user1, user2));

        // then
        assertThrows(ConstraintViolationException.class, () -> userService.findAllUsers(pageDto2));
    }

    @Test
    void findUserByIdCorrectDataShouldReturnUserDtoTest() {
        // given
        long id = 1;
        when(userDao.findById(any(Long.class))).thenReturn(Optional.of(user1));

        // when
        UserDto actual = userService.findUserById(id);

        // then
        assertEquals(userDto1, actual);
    }

    @Test
    void findUserByIdCorrectDataShouldThrowExceptionTest() {
        // given
        long id = 1;
        when(userDao.findById(any(Long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(id));
    }

    @Test
    void findUserByIdIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = -1;
        when(userDao.findById(any(Long.class))).thenReturn(Optional.of(user1));

        // then
        assertThrows(ConstraintViolationException.class, () -> userService.findUserById(id));
    }

    @Test
    void findUserByHighestCostOfAllOrdersCorrectDataShouldReturnUserDtoTest() {
        // given
        when(userDao.findByHighestCostOfAllOrders()).thenReturn(Optional.of(user1));

        // when
        UserDto actual = userService.findUserByHighestCostOfAllOrders();

        // then
        assertEquals(userDto1, actual);
    }

    @Test
    void findUserByHighestCostOfAllOrdersCorrectDataShouldThrowExceptionTest() {
        // given
        when(userDao.findByHighestCostOfAllOrders()).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> userService.findUserByHighestCostOfAllOrders());
    }
}