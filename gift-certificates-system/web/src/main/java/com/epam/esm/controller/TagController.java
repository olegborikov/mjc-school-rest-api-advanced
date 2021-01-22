package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> getTags() {
        List<TagDto> foundTagsDto = tagService.findAllTags();
        foundTagsDto.forEach(foundTagDto -> addRelationships(foundTagDto, foundTagDto.getId()));
        return foundTagsDto;
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable long id) {
        TagDto foundTagDto = tagService.findTagById(id);
        addRelationships(foundTagDto, foundTagDto.getId());
        return foundTagDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@RequestBody TagDto tagDto) {
        TagDto addedTagDto = tagService.addTag(tagDto);
        addRelationships(addedTagDto, addedTagDto.getId());
        return addedTagDto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable long id) {
        tagService.removeTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void addRelationships(TagDto tagDto, long id) {
        tagDto.add(linkTo(methodOn(TagController.class).getTagById(id)).withSelfRel());
        tagDto.add(linkTo(methodOn(TagController.class).deleteTag(id)).withRel("delete"));
    }
}
