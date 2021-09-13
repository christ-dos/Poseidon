package com.nnk.springboot.exceptions;

public class RuleNameNotFoundException extends RuntimeException{
    public RuleNameNotFoundException(String message) {
        super(message);
    }
}
