package com.nnk.springboot.exceptions;

/**
 * Class that handles exception when the bidList is not found
 *
 * @author Christine Duarte
 */
public class BidListNotFoundException extends RuntimeException {
    /**
     * Constructor
     *
     * @param message A string containing the message that is sent if the exception in handling
     */
    public BidListNotFoundException(String message) {
        super(message);
    }
}
