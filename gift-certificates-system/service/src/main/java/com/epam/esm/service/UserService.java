package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
public interface UserService {

    List<UserDto> findAllUsers(@Valid PageDto pageDto);

    UserDto findUserById(@Valid @Min(value = 1, message = ExceptionMessageKey.INCORRECT_USER_ID) long id)
            throws IncorrectParameterValueException, ResourceNotFoundException;
}
