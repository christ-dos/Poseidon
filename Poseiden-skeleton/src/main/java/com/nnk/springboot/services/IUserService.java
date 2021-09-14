package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();

    Optional getUserById(Integer id);

    User addUser(User user);

    User updateUser(User user);

    String deleteUser(Integer id);
}
