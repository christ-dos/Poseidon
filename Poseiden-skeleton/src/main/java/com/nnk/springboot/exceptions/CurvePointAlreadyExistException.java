package com.nnk.springboot.exceptions;

public class CurvePointAlreadyExistException extends RuntimeException {
    public CurvePointAlreadyExistException(String message) {
        super(message);
    }
}
