package com.nnk.springboot.exceptions;

public class RatingAlreadyExistException  extends RuntimeException{
    public RatingAlreadyExistException(String message) {
        super(message);
    }
}
