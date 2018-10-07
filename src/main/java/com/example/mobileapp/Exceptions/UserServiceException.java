package com.example.mobileapp.Exceptions;

public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = -6929343097704245120L;

    public UserServiceException(String message) {
        super(message);
    }
}
