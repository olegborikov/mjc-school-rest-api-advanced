package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParametersException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        when(tagDao.add(any(Tag.class))).thenReturn(tag);
        TagDto actual = tagService.addTag(tagDto);
        assertEquals(expected, actual);
    }

    @Test
    void addTagIncorrectDataShouldThrowException() {
        Tag tag = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        TagDto tagDto = TagDto.builder()
                .name(" ")
                .build();
        when(tagDao.add(any(Tag.class))).thenReturn(tag);
        assertThrows(IncorrectParametersException.class, () -> {
            tagService.addTag(tagDto);
        });
    }

    @Test
    void findAllTagsCorrectDataShouldReturnListOfTagDto() {
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
        List<TagDto> actual = tagService.findAllTags();
        assertEquals(expected, actual.size());
    }

    @Test
    void findTagByIdCorrectDataShouldReturnTagDto() {
        Tag tag = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        TagDto expected = TagDto.builder()
                .id(1L)
                .name("Sport")
                .build();
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.of(tag));
        String id = "1";
        TagDto actual = tagService.findTagById(id);
        assertEquals(expected, actual);
    }

    @Test
    void findTagByIdCorrectDataShouldThrowException() {
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.empty());
        String id = "1";
        assertThrows(ResourceNotFoundException.class, () -> {
            tagService.findTagById(id);
        });
    }

    @Test
    void findTagByIdIncorrectDataShouldThrowException() {
        Tag tag = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.of(tag));
        String id = "a";
        assertThrows(IncorrectParametersException.class, () -> {
            tagService.findTagById(id);
        });
    }

    @Test
    void removeTagCorrectDataShouldNotThrowException() {
        when(tagDao.remove(any(Long.class))).thenReturn(true);
        String id = "1";
        tagService.removeTag(id);
    }

    @Test
    void removeTagCorrectDataShouldThrowException() {
        when(tagDao.remove(any(Long.class))).thenReturn(false);
        String id = "5";
        assertThrows(ResourceNotFoundException.class, () -> {
            tagService.removeTag(id);
        });
    }

    @Test
    void removeTagIncorrectDataShouldThrowException() {
        when(tagDao.remove(any(Long.class))).thenReturn(true);
        String id = "a";
        assertThrows(IncorrectParametersException.class, () -> {
            tagService.removeTag(id);
        });
    }
}
