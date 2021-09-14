package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class of service that manage {@link User} entity
 *
 * @author Christine Duarte
 */
@Service
@Slf4j
public class UserService {

    /**
     * An instance Of {@link UserRepository}
     */
    private UserRepository userRepository;

    /**
     * Constructor
     *
     * @param userRepository An instance of {@link UserRepository}
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method that get all {@link User}
     *
     * @return A list of {@link User}
     */
    public List<User> getUsers() {
        log.info("Service: displaying Users");
        return userRepository.findAll();
    }

    /**
     * Method that get a {@link User} by Id
     *
     * @param id An Integer containing the id of the User
     * @return An instance of {@link User}
     */
    public Optional getUserById(Integer id) {
        Optional user = userRepository.findById(id);
        if (!user.isPresent()) {
            log.error("Service: User NOT FOUND with ID: " + id);
            throw new UserNotFoundException("User not found");
        }
        log.info("Service: User found with ID: " + id);

        return user;
    }

    /**
     * Method which add a {@link User}
     *
     * @param user An instance {@link User}
     * @return The {@link User} saved
     */
    public User addUser(User user) {
        log.info("Service: User saved");
        user.setPassword(encryptedPassword(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Method which update a {@link User}
     *
     * @param user An instance {@link User}
     * @return the {@link User} updated
     */
    public User updateUser(User user) {
        User userToUpdate = userRepository.getById(user.getId());
        if (userToUpdate == null) {
            log.error("Service: User NOT FOUND with ID: " + user.getId());
            throw new UserNotFoundException("User not found");
        }
        userToUpdate.setFullname(user.getFullname());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(encryptedPassword(user.getPassword()));
        userToUpdate.setRole(user.getRole());
        log.info("Service: User updated with ID: " + user.getId());

        return userRepository.save(userToUpdate);
    }

    /**
     * Method that delete a {@link User}
     *
     * @param id An integer containing the id
     * @return A String containing "User deleted"
     */
    public String deleteUser(Integer id) {
        userRepository.deleteById(id);
        log.info("Service: User deleted with ID:" + id);

        return "User deleted";
    }

    /**
     * Method private which encrypt the password before recording in Database
     *
     * @param password A String containing the password unencrypted
     * @return A String containing the password encrypted
     */
    private String encryptedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
