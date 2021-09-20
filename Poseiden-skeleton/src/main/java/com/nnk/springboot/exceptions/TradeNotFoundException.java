package com.nnk.springboot.exceptions;

/**
 * Class that handles exception when the trade is not found
 *
 * @author Christine Duarte
 */
public class TradeNotFoundException extends RuntimeException{
    /**
     * Constructor
     *
     * @param message A string containing the message that is sent if the exception in handling
     */
    public TradeNotFoundException(String message) {
        super(message);
    }
}
