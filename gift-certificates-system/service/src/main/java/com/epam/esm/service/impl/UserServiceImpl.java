package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> foundUsers = userDao.findAll();
        return foundUsers.stream()
                .map(foundUser -> modelMapper.map(foundUser, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserById(long id) throws IncorrectParameterValueException, ResourceNotFoundException {
        UserValidator.validateId(id);
        Optional<User> foundUserOptional = userDao.findById(id);
        return foundUserOptional.map(foundUser -> modelMapper.map(foundUser, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionMessageKey.USER_NOT_FOUND_BY_ID, String.valueOf(id)));
    }
}