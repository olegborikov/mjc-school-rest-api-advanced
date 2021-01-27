package com.epam.esm.controller;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code UserController} is an endpoint of the API which allows to perform operations on users.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/users".
 * So that {@code UserController} is accessed by sending request to /users.
 *
 * @author Oleg Borikov
 * @since 1.0
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users.
     * Annotated by {@link GetMapping} with no parameters.
     * Therefore, processes GET requests at /users.
     *
     * @param page the number of page for pagination
     * @param size the size of page for pagination
     * @return the list of all users dto
     */
    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false, defaultValue = "1") int page,
                                  @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(page, size);
        List<UserDto> foundUsersDto = userService.findAllUsers(pageDto);
        foundUsersDto.forEach(this::addRelationship);
        return foundUsersDto;
    }

    /**
     * Get user by id.
     * Annotated by {@link GetMapping} with parameter value = "/{id}".
     * Therefore, processes GET requests at /users/{id}.
     *
     * @param id the user id which will be found. Inferred from the request URI
     * @return the found user dto
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        UserDto foundUserDto = userService.findUserById(id);
        addRelationship(foundUserDto);
        return foundUserDto;
    }

    private void addRelationship(UserDto userDto) {
        userDto.add(linkTo(methodOn(UserController.class).getUserById(userDto.getId())).withSelfRel());
    }
}
