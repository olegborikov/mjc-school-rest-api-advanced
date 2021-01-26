package com.epam.esm.controller;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

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
    public List<TagDto> getTags(@RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(page, size);
        List<TagDto> foundTagsDto = tagService.findAllTags(pageDto);
        foundTagsDto.forEach(this::addRelationship);
        return foundTagsDto;
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable long id) {
        TagDto foundTagDto = tagService.findTagById(id);
        addRelationship(foundTagDto);
        return foundTagDto;
    }

    @GetMapping("/popular")
    public TagDto getMostPopularTagOfUserWithHighestCostOfAllOrders() {
        TagDto foundTagDto = tagService.findMostPopularTagOfUserWithHighestCostOfAllOrders();
        addRelationship(foundTagDto);
        return foundTagDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@RequestBody TagDto tagDto) {
        TagDto addedTagDto = tagService.addTag(tagDto);
        addRelationship(addedTagDto);
        return addedTagDto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable long id) {
        tagService.removeTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void addRelationship(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel());
        tagDto.add(linkTo(methodOn(TagController.class).deleteTag(tagDto.getId())).withRel("delete"));
    }
}
