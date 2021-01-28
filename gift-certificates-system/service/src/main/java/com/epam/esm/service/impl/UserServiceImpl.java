package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class {@code UserServiceImpl} is implementation of interface {@link UserService}
 * and intended to work with {@link com.epam.esm.entity.User}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
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
    public List<UserDto> findAllUsers(PageDto pageDto) {
        Page page = modelMapper.map(pageDto, Page.class);
        List<User> foundUsers = userDao.findAll(page);
        return foundUsers.stream()
                .map(foundUser -> modelMapper.map(foundUser, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserById(long id) throws ResourceNotFoundException {
        Optional<User> foundUserOptional = userDao.findById(id);
        return foundUserOptional.map(foundUser -> modelMapper.map(foundUser, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionMessageKey.USER_NOT_FOUND_BY_ID, String.valueOf(id)));
    }

    @Override
    public UserDto findUserByHighestCostOfAllOrders() throws ResourceNotFoundException {
        Optional<User> foundUserOptional = userDao.findByHighestCostOfAllOrders();
        return foundUserOptional.map(foundUser -> modelMapper.map(foundUser, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionMessageKey.USER_NOT_FOUND_BY_HIGHEST_COST_OF_ALL_ORDERS));
    }
}
