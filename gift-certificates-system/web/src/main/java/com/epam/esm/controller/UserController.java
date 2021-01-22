package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        List<UserDto> foundUsersDto = userService.findAllUsers();
        foundUsersDto.forEach(this::addRelationship);
        return foundUsersDto;
    }

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
