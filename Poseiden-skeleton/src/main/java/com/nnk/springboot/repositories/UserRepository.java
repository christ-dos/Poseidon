package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Interface that handles database queries for user
 *
 * @author Christine Duarte
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * Query to find a user by username
     * @param username A String containing the username of the user
     * @return An instance of User
     */
    User findByUsername(String username);
}
