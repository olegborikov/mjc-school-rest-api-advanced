package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.IncorrectParametersValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.handler.ErrorHandler;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> tags() {
        return tagService.findAllTags();
    }

    @GetMapping("/{id}")
    public TagDto tagById(@PathVariable String id) {
        return tagService.findTagById(id);
    }

    @PostMapping
    public TagDto addTag(@RequestBody TagDto tagDto) {
        return tagService.addTag(tagDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable String id) {
        tagService.removeTag(id);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorHandler handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorHandler(exception.getMessage(), 44);
    }

    @ExceptionHandler(value = IncorrectParametersValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorHandler handleIncorrectParametersValueException(IncorrectParametersValueException exception) {
        return new ErrorHandler(exception.getMessage(), 40);
    }
}
