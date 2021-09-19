package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UserNotFoundException;

import java.util.List;

public interface IUserService {
    List<User> getUsers();

    User getUserById(Integer id) throws UserNotFoundException;

    User addUser(User user);

    User updateUser(User user);

    String deleteUser(Integer id);
}
