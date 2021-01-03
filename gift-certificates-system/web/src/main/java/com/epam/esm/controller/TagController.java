package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
public class TagController {
    private TagService tagService;

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> findTags() {
        return tagService.findAllTags();
    }

    @GetMapping("/{id}")
    public Tag findTagsById(@PathVariable long id) throws NotFoundException {
        Optional<Tag> foundTagOptional = tagService.findTagById(id);
        return foundTagOptional.orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Tag addTag(@RequestBody Tag tag) {
        boolean isAdded = tagService.addTag(tag);
        if (isAdded) {
            return tag;
        } else {
            return null;// TODO: 03.01.2021  
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable long id) throws NotFoundException {
        boolean isRemoved = tagService.removeTag(id);
        if(!isRemoved){
            throw new NotFoundException();
        }
    }
}
