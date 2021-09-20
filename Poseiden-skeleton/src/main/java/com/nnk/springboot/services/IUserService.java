package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UserNotFoundException;

import java.util.List;

/**
 * An Interface for {@link TradeService}
 *
 * @author Christine Duarte
 */
public interface IUserService {
    /**
     * Method that get all {@link User}
     *
     * @return A list of {@link User}
     */
    List<User> getUsers();

    /**
     * Method that get a {@link User} by id
     *
     * @param id An Integer containing the id of the User
     * @return An instance of {@link User}
     */
    User getUserById(Integer id) throws UserNotFoundException;

    /**
     * Method which add a {@link User}
     *
     * @param user An instance {@link User}
     * @return The {@link User} saved
     */
    User addUser(User user);

    /**
     * Method which update a {@link User}
     *
     * @param user An instance {@link User}
     * @return the {@link User} updated
     */
    User updateUser(User user);

    /**
     * Method that delete a {@link User}
     *
     * @param id An integer containing the id
     * @return A String containing "User deleted"
     */
    String deleteUser(Integer id);
}
