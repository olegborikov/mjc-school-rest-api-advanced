package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

class TagServiceImplTest {

    private static TagDao tagDao;
    private static ModelMapper modelMapper;
    private static TagService tagService;
    private static Tag tag1;
    private static Tag tag2;
    private static TagDto tagDto1;
    private static TagDto tagDto2;
    private static TagDto tagDto3;

    @BeforeAll
    static void setUp() {
        tagDao = mock(TagDaoImpl.class);
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        tagService = new TagServiceImpl(tagDao, modelMapper);
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
    }

    @AfterAll
    static void tearDown() {
        tagDao = null;
        modelMapper = null;
        tagService = null;
        tag1 = null;
        tag2 = null;
        tagDto1 = null;
        tagDto2 = null;
        tagDto3 = null;
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
    void addTagIncorrectDataShouldThrowExceptionTest() {
        // given
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());
        when(tagDao.add(any(Tag.class))).thenReturn(tag1);

        // then
        assertThrows(IncorrectParameterValueException.class, () -> tagService.addTag(tagDto3));
    }

    @Test
    void findAllTagsCorrectDataShouldReturnListOfTagDtoTest() {
        // given
        int expected = 2;
        when(tagDao.findAll()).thenReturn(Arrays.asList(tag1, tag2));

        // when
        List<TagDto> actual = tagService.findAllTags();

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findTagByIdCorrectDataShouldReturnTagDtoTest() {
        // given
        long id = 1;
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.of(tag1));

        // when
        TagDto actual = tagService.findTagById(id);

        // then
        assertEquals(tagDto2, actual);
    }

    @Test
    void findTagByIdCorrectDataShouldThrowExceptionTest() {
        // given
        long id = 5;
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> tagService.findTagById(id));
    }

    @Test
    void findTagByIdIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = -1;
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.of(tag1));

        // then
        assertThrows(IncorrectParameterValueException.class, () -> tagService.findTagById(id));
    }

    @Test
    void removeTagCorrectDataShouldNotThrowExceptionTest() {
        // given
        long id = 1;
        doNothing().when(tagDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(tagDao).remove(any(Long.class));

        // then
        assertDoesNotThrow(() -> tagService.removeTag(id));
    }

    @Test
    void removeTagIncorrectDataShouldThrowExceptionTest() {
        // given
        long id = -1;
        doNothing().when(tagDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(tagDao).remove(any(Long.class));

        // then
        assertThrows(IncorrectParameterValueException.class, () -> tagService.removeTag(id));
    }

    @Test
    void findTagsByGiftCertificateIdCorrectDataShouldReturnListOfTagDtoTest() {
        // given
        int expected = 2;
        long giftCertificateId = 1;
        when(tagDao.findByGiftCertificateId(any(Long.class))).thenReturn(Arrays.asList(tag1, tag2));

        // when
        List<TagDto> actual = tagService.findTagsByGiftCertificateId(giftCertificateId);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findTagsByGiftCertificateIdIncorrectDataShouldThrowExceptionTest() {
        // given
        long giftCertificateId = -1L;
        when(tagDao.findByGiftCertificateId(any(Long.class))).thenReturn(Arrays.asList(tag1, tag2));

        // then
        assertThrows(IncorrectParameterValueException.class,
                () -> tagService.findTagsByGiftCertificateId(giftCertificateId));
    }
}
