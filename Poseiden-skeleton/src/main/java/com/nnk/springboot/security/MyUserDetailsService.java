package com.nnk.springboot.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Class that manage the user's data of connection
 * implements {@link UserDetailsService} A Core interface which loads user-specific data.
 *
 * @author Christine Duarte
 */
@Service
@Slf4j
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    /**
     * An instance of {@link UserRepository} to load information to login the user
     */
    private final UserRepository userRepository;

    /**
     * The Constructor
     *
     * @param userRepository An instance of {@link UserRepository}
     */
    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method Overwritten of the Interface {@link UserDetailsService}
     * that permit get the information for login from the database
     * and create an Instance of  {@link UserDetails}
     *
     * @param username A String containing the username, that is the email of the user
     * @return An instance of {@link MyUserDetails} with an email and password
     * @throws UsernameNotFoundException If the User not exist in datbase
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("MyUserDetailsService: UserNotFound ");
            throw new UsernameNotFoundException("User not found:" + username);
        }
        log.debug("MyUserDetailsService: User found with username :" + username +  " " + user.getRole() + user.getFullname());
        //TODO retirer le log
        return new MyUserDetails(user.getUsername(), user.getPassword(), user.getRole());
    }
}
