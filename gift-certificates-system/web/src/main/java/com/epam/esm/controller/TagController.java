package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private TagService tagService;

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> tags() {
        return tagService.findAllTags();
    }

    @GetMapping("/{id}")
    public TagDto tagById(@PathVariable long id) {
        return tagService.findTagById(id);
    }

    @PostMapping
    public TagDto addTag(@RequestBody TagDto tagDto) {
        return tagService.addTag(tagDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable long id) {
        tagService.removeTag(id);
    }

}
