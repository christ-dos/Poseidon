package com.nnk.springboot.exceptions;

/**
 * Class that handles exception when the curvePoint is not found
 *
 * @author Christine Duarte
 */
public class CurvePointNotFoundException extends RuntimeException {
    /**
     * Constructor
     *
     * @param message A string containing the message that is sent if the exception in handling
     */
    public CurvePointNotFoundException(String message) {
        super(message);
    }
}
