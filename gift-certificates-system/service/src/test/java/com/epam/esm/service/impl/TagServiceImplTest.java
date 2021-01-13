package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    private TagDao tagDao;
    private ModelMapper modelMapper;
    private TagService tagService;

    @BeforeEach
    void setUp() {
        tagDao = mock(TagDaoImpl.class);
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        tagService = new TagServiceImpl(tagDao, modelMapper);
    }

    @AfterEach
    void tearDown() {
        tagDao = null;
        modelMapper = null;
        tagService = null;
    }

    @Test
    void addTagCorrectDataShouldReturnTagDto() {
        // given
        Tag tag = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        TagDto tagDto = TagDto.builder()
                .name("Sport")
                .build();
        TagDto expected = TagDto.builder()
                .id(1L)
                .name("Sport")
                .build();
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());
        when(tagDao.add(any(Tag.class))).thenReturn(tag);

        // when
        TagDto actual = tagService.addTag(tagDto);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void addTagIncorrectDataShouldThrowException() {
        // given
        Tag tag = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        TagDto tagDto = TagDto.builder()
                .name(" ")
                .build();
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());
        when(tagDao.add(any(Tag.class))).thenReturn(tag);

        // then
        assertThrows(IncorrectParameterValueException.class, () -> tagService.addTag(tagDto));
    }

    @Test
    void findAllTagsCorrectDataShouldReturnListOfTagDto() {
        // given
        Tag tag1 = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        Tag tag2 = Tag.builder()
                .id(2L)
                .name("Travel")
                .build();
        int expected = 2;
        when(tagDao.findAll()).thenReturn(Arrays.asList(tag1, tag2));

        // when
        List<TagDto> actual = tagService.findAllTags();

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findTagByIdCorrectDataShouldReturnTagDto() {
        // given
        Tag tag = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        TagDto expected = TagDto.builder()
                .id(1L)
                .name("Sport")
                .build();
        Long id = 1L;
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.of(tag));

        // when
        TagDto actual = tagService.findTagById(id);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void findTagByIdCorrectDataShouldThrowException() {
        // given
        Long id = 5L;
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> tagService.findTagById(id));
    }

    @Test
    void findTagByIdIncorrectDataShouldThrowException() {
        // given
        Tag tag = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        Long id = -1L;
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.of(tag));

        // then
        assertThrows(IncorrectParameterValueException.class, () -> tagService.findTagById(id));
    }

    @Test
    void removeTagCorrectDataShouldNotThrowException() {
        // given
        Long id = 1L;
        doNothing().when(tagDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(tagDao).remove(any(Long.class));

        // then
        assertDoesNotThrow(() -> tagService.removeTag(id));
    }

    @Test
    void removeTagIncorrectDataShouldThrowException() {
        // given
        Long id = -1L;
        doNothing().when(tagDao).removeGiftCertificateHasTag(any(Long.class));
        doNothing().when(tagDao).remove(any(Long.class));

        // then
        assertThrows(IncorrectParameterValueException.class, () -> tagService.removeTag(id));
    }

    @Test
    void findTagsByGiftCertificateIdCorrectDataShouldReturnListOfTagDto() {
        // given
        Tag tag1 = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        Tag tag2 = Tag.builder()
                .id(2L)
                .name("Travel")
                .build();
        int expected = 2;
        Long giftCertificateId = 1L;
        when(tagDao.findByGiftCertificateId(any(Long.class))).thenReturn(Arrays.asList(tag1, tag2));

        // when
        List<TagDto> actual = tagService.findTagsByGiftCertificateId(giftCertificateId);

        // then
        assertEquals(expected, actual.size());
    }

    @Test
    void findTagsByGiftCertificateIdIncorrectDataShouldThrowException() {
        // given
        Tag tag1 = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        Tag tag2 = Tag.builder()
                .id(2L)
                .name("Travel")
                .build();
        Long giftCertificateId = -1L;
        when(tagDao.findByGiftCertificateId(any(Long.class))).thenReturn(Arrays.asList(tag1, tag2));

        // then
        assertThrows(IncorrectParameterValueException.class,
                () -> tagService.findTagsByGiftCertificateId(giftCertificateId));
    }
}
