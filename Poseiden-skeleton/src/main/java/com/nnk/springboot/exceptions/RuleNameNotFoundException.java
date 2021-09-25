package com.nnk.springboot.exceptions;

/**
 * Class that handles exception when the ruleName is not found
 *
 * @author Christine Duarte
 */
public class RuleNameNotFoundException extends RuntimeException{
    /**
     * Constructor
     *
     * @param message A string containing the message that is sent if the exception in handling
     */
    public RuleNameNotFoundException(String message) {
        super(message);
    }
}
