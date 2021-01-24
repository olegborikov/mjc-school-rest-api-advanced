package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

public interface UserService {

    List<UserDto> findAllUsers(PageDto pageDto);

    UserDto findUserById(long id) throws IncorrectParameterValueException, ResourceNotFoundException;
}
