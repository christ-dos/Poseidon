package com.nnk.springboot.exceptions;

/**
 * Class that handles exception when the user is not found
 *
 * @author Christine Duarte
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Constructor
     *
     * @param message A string containing the message that is sent if the exception in handling
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
