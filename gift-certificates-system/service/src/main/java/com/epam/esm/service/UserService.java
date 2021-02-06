package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Interface {@code UserService} describes abstract behavior for working with {@link com.epam.esm.entity.User}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Validated
public interface UserService {

    /**
     * Find all users.
     *
     * @param pageDto the pageDto containing information about pagination
     * @return the list of all users dto
     */
    List<UserDto> findAllUsers(@Valid @NotNull PageDto pageDto);

    /**
     * Find user by id.
     *
     * @param id the id of user which will be searched
     * @return the found user dto
     * @throws ResourceNotFoundException an exception thrown in case user with such id not found
     */
    UserDto findUserById(@Min(value = 1, message = ExceptionMessageKey.INCORRECT_USER_ID) long id)
            throws ResourceNotFoundException;

    /**
     * Find user by highest cost of all orders.
     *
     * @return the found user dto
     * @throws ResourceNotFoundException an exception thrown in case user not found
     */
    UserDto findUserByHighestCostOfAllOrders() throws ResourceNotFoundException;
}
