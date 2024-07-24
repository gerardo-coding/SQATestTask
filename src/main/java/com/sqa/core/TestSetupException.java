package com.sqa.core;

/**
 * Custom Exception to throw if the test setup fails
 */
public class TestSetupException extends RuntimeException{
    public TestSetupException(String message){
        super(message);
    }
}