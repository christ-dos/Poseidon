package com.nnk.springboot.exceptions;

/**
 * Class that handles exception when the rating is not found
 *
 * @author Christine Duarte
 */
public class RatingNotFoundException extends RuntimeException{
    /**
     * Constructor
     *
     * @param message A string containing the message that is sent if the exception in handling
     */
    public RatingNotFoundException(String message) {
        super(message);
    }
}
