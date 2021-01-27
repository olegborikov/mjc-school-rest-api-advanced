package com.epam.esm.service.impl;

import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceConfiguration.class)
@Import(ValidationAutoConfiguration.class)
class TagServiceImplTest {

    @MockBean
    private TagDao tagDao;
    @MockBean
    private UserService userService;
    @Autowired
    private TagService tagService;
    private static Tag tag1;
    private static Tag tag2;
    private static TagDto tagDto1;
    private static TagDto tagDto2;
    private static TagDto tagDto3;
    private static PageDto pageDto1;
    private static PageDto pageDto2;
    private static UserDto userDto1;

    @BeforeAll
    static void setUp() {
        tag1 = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        tag2 = Tag.builder()
                .id(2L)
                .name("Travel")
                .build();
        tagDto1 = TagDto.builder()
                .name("Sport")
                .build();
        tagDto2 = TagDto.builder()
                .id(1L)
                .name("Sport")
                .build();
        tagDto3 = TagDto.builder()
                .name(" ")
                .build();
        pageDto1 = PageDto.builder()
                .number(1)
                .size(5)
                .build();
        pageDto2 = PageDto.builder()
                .number(-3)
                .size(3)
                .build();
        userDto1 = UserDto.builder()
                .id(1L)
                .name("Oleg")
                .build();
    }

    @AfterAll
    static void tearDown() {
        tag1 = null;
        tag2 = null;
        tagDto1 = null;
        tagDto2 = null;
        tagDto3 = null;
        pageDto1 = null;
        pageDto2 = null;
        userDto1 = null;
    }

    @Test
    void addTagCorrectDataShouldReturnTagDtoTest() {
        // given
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());
        when(tagDao.add(any(Tag.class))).thenReturn(tag1);

        // when
        TagDto actual = tagService.addTag(tagDto1);

        // then
        assertEquals(tagDto2, actual);
    }

    @Test
    void addTagCorrectDataShouldThrowExceptionTest() {
        // given
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.of(tag1));
        when(tagDao.add(any(Tag.class))).thenReturn(tag1);

        // then
        assertThrows(ResourceExistsException.class, () -> tagService.addTag(tagDto1));
    }

    @Test
    void addTagIncorrectDataShouldThrowExceptionTest() {
        // given
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());
        when(tagDao.add(any(Tag.class))).thenReturn(tag1);

        // then
        assertThrows(ConstraintViolationException.class, () -> tagService.addTag(tagDto3));
    }

    @Test
    void findAllTagsCorrectDataShouldReturnListOfTagDtoTest() {
        // given
        int expected = 2;
        when(tagDao.findAll(any(Page.class))).thenReturn(Arrays.asList(tag1, tag2));

        // when
        List<TagDto> actual = tagService.findAllTags(pageDto1);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findAllTagsIncorrectDataShouldThrowExceptionTest() {
        // given
        when(tagDao.findAll(any(Page.class))).thenReturn(Arrays.asList(tag1, tag2));

        // then
        assertThrows(ConstraintViolationException.class, () -> tagService.findAllTags(pageDto2));
    }

    @Test
    void findTagByIdCorrectDataShouldReturnTagDtoTest() {
        // given
        long id = 1;
        when(tagDao.findById(any(long.class))).thenReturn(Optional.of(tag1));

        // when
        TagDto actual = tagService.findTagById(id);

        // then
        assertEquals(tagDto2, actual);
    }

    @Test
    void findTagByIdCorrectDataShouldThrowExceptionTest() {
        // given
        long id = 5;
        when(tagDao.findById(any(long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> tagService.findTagById(id));
    }

    @Test
    void findTagByIdIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = -1;
        when(tagDao.findById(any(long.class))).thenReturn(Optional.of(tag1));

        // then
        assertThrows(ConstraintViolationException.class, () -> tagService.findTagById(id));
    }

    @Test
    void findTagByNameCorrectDataShouldReturnTagDtoTest() {
        // given
        String name = "Sport";
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.of(tag1));

        // when
        TagDto actual = tagService.findTagByName(name);

        // then
        assertEquals(tagDto2, actual);
    }

    @Test
    void findTagByNameCorrectDataShouldThrowExceptionTest() {
        // given
        String name = "Sport";
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> tagService.findTagByName(name));
    }

    @Test
    void findTagByNameIncorrectDataShouldThrowExceptionTest() {
        // given
        String name = " ";
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.of(tag1));

        // then
        assertThrows(ConstraintViolationException.class, () -> tagService.findTagByName(name));
    }

    @Test
    void isExistsCorrectDataShouldReturnTrueTest() {
        // given
        String name = "Sport";
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.of(tag1));

        // when
        boolean actual = tagService.isExists(name);

        // then
        assertTrue(actual);
    }

    @Test
    void isExistsCorrectDataShouldReturnFalseTest() {
        // given
        String name = "Sport";
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());

        // when
        boolean actual = tagService.isExists(name);

        // then
        assertFalse(actual);
    }

    @Test
    void removeTagCorrectDataShouldNotThrowExceptionTest() {
        // given
        long id = 1;
        when(tagDao.findById(any(long.class))).thenReturn(Optional.of(tag1));
        doNothing().when(tagDao).removeGiftCertificateHasTag(any(long.class));
        doNothing().when(tagDao).remove(any(long.class));

        // then
        assertDoesNotThrow(() -> tagService.removeTag(id));
    }

    @Test
    void removeTagIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = -1;
        when(tagDao.findById(any(long.class))).thenReturn(Optional.of(tag1));
        doNothing().when(tagDao).removeGiftCertificateHasTag(any(long.class));
        doNothing().when(tagDao).remove(any(long.class));

        // then
        assertThrows(ConstraintViolationException.class, () -> tagService.removeTag(id));
    }

    @Test
    void findMostPopularTagOfUserWithHighestCostOfAllOrdersShouldReturnTagDtoTest() {
        // given
        when(userService.findUserByHighestCostOfAllOrders()).thenReturn(userDto1);
        when(tagDao.findMostPopularOfUser(any(long.class))).thenReturn(Optional.of(tag1));

        // when
        TagDto actual = tagService.findMostPopularTagOfUserWithHighestCostOfAllOrders();

        // then
        assertEquals(tagDto2, actual);
    }

    @Test
    void findMostPopularTagOfUserWithHighestCostOfAllOrdersShouldThrowExceptionTest() {
        // given
        when(userService.findUserByHighestCostOfAllOrders()).thenReturn(userDto1);
        when(tagDao.findMostPopularOfUser(any(long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> tagService.findMostPopularTagOfUserWithHighestCostOfAllOrders());
    }
}
