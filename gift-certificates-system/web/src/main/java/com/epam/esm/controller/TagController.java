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

/**
 * Class {@code TagController} is an endpoint of the API which allows to perform CRD operations on tags.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/tags".
 * So that {@code TagController} is accessed by sending request to /tags.
 *
 * @author Oleg Borikov
 * @since 1.0
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Get all tags.
     * Annotated by {@link GetMapping} with no parameters.
     * Therefore, processes GET requests at /tags.
     *
     * @param page the number of page for pagination
     * @param size the size of page for pagination
     * @return the list of all tags dto
     */
    @GetMapping
    public List<TagDto> getTags(@RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(page, size);
        List<TagDto> foundTagsDto = tagService.findAllTags(pageDto);
        foundTagsDto.forEach(this::addRelationship);
        return foundTagsDto;
    }

    /**
     * Get tag by id.
     * Annotated by {@link GetMapping} with parameter value = "/{id}".
     * Therefore, processes GET requests at /tags/{id}.
     *
     * @param id the tag id which will be found. Inferred from the request URI
     * @return the found tag dto
     */
    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable long id) {
        TagDto foundTagDto = tagService.findTagById(id);
        addRelationship(foundTagDto);
        return foundTagDto;
    }

    /**
     * Get most popular tag of user with highest cost of all orders.
     * Annotated by {@link GetMapping} with no parameters.
     * Therefore, processes GET requests at /tags/popular.
     *
     * @return the found tag dto
     */
    @GetMapping("/popular")
    public TagDto getMostPopularTagOfUserWithHighestCostOfAllOrders() {
        TagDto foundTagDto = tagService.findMostPopularTagOfUserWithHighestCostOfAllOrders();
        addRelationship(foundTagDto);
        return foundTagDto;
    }

    /**
     * Add new tag.
     * Annotated by {@link PostMapping} with no parameters.
     * Therefore, processes POST requests at /tags.
     *
     * @param tagDto the new tag which will be added
     * @return the new tag dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@RequestBody TagDto tagDto) {
        TagDto addedTagDto = tagService.addTag(tagDto);
        addRelationship(addedTagDto);
        return addedTagDto;
    }

    /**
     * Delete tag with the specified id.
     * Annotated by {@link DeleteMapping} with parameter value = "/{id}".
     * Therefore, processes DELETE requests at /tags/{id}.
     *
     * @param id the tag id which will be deleted. Inferred from the request URI
     */
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
