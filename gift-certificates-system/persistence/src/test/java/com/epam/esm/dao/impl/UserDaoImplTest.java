package com.epam.esm.dao.impl;

import com.epam.esm.configuration.PersistenceConfiguration;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfiguration.class)
class UserDaoImplTest {

    private final UserDao userDao;
    private static User user1;
    private static Page page1;
    private static Page page2;
    private static Page page3;

    @BeforeAll
    static void setUp() {
        user1 = User.builder()
                .id(1L)
                .name("Oleg")
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
        user1 = null;
        page1 = null;
        page2 = null;
        page3 = null;
    }

    @Autowired
    public UserDaoImplTest(UserDao userDao) {
        this.userDao = userDao;
    }

    @Test
    void addShouldThrowExceptionTest() {
        // given
        User user = new User();

        // then
        assertThrows(UnsupportedOperationException.class, () -> userDao.add(user));
    }

    @Test
    void findAllCorrectDataShouldReturnListOfUsersTest() {
        // given
        long expected = 3;

        // when
        List<User> actual = userDao.findAll(page1);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findAllCorrectDataShouldReturnEmptyListTest() {
        // when
        List<User> actual = userDao.findAll(page2);

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    void findAllIncorrectDataShouldThrowExceptionTest() {
        // then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userDao.findAll(page3));
    }

    @Test
    void findByIdCorrectDataShouldReturnUserOptionalTest() {
        // given
        long id = 1;

        // when
        Optional<User> actual = userDao.findById(id);

        // then
        assertEquals(Optional.of(user1), actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnEmptyOptionalTest() {
        // given
        long id = 8;

        // when
        Optional<User> actual = userDao.findById(id);

        // then
        assertFalse(actual.isPresent());
    }

    @Test
    void updateShouldThrowExceptionTest() {
        // given
        User user = new User();

        // then
        assertThrows(UnsupportedOperationException.class, () -> userDao.update(user));
    }

    @Test
    void removeShouldThrowExceptionTest() {
        // given
        long id = 1;

        // then
        assertThrows(UnsupportedOperationException.class, () -> userDao.remove(id));
    }
}
