package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * Class {@code TagController} is an endpoint of the API
 * which allows to perform CRD operations on tags.
 * Annotated by {@link RestController} with no parameters
 * to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/tags". So that
 * {@code TagController} is accessed by sending request to /tags.
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
     * @return the list of all tags dto
     */
    @GetMapping
    public List<TagDto> getTags() {
        return tagService.findAllTags();
    }

    /**
     * Get tag by id.
     * Annotated by {@link GetMapping} with parameter value = "/{id}".
     * Therefore, processes GET requests at /tags/{id}.
     *
     * @param id the tag id, which will be found. Inferred from the request URI
     * @return the found tag dto
     */
    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable Long id) {
        return tagService.findTagById(id);
    }

    /**
     * Add new tag.
     * Annotated by {@link PostMapping} with no parameters.
     * Therefore, processes POST requests at /tags.
     *
     * @param tagDto the new tag, which will be added
     * @return the new tag dto
     */
    @PostMapping
    public TagDto addTag(@RequestBody TagDto tagDto) {
        return tagService.addTag(tagDto);
    }

    /**
     * Delete tag with the specified id.
     * Annotated by {@link DeleteMapping} with parameter value = "/{id}".
     * Therefore, processes DELETE requests at /tags/{id}.
     *
     * @param id the tag id, which will be deleted. Inferred from the request URI
     */
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.removeTag(id);
    }
}
