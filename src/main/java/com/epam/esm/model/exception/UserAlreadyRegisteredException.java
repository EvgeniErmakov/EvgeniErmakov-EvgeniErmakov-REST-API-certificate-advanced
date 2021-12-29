package com.epam.esm.model.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

    private static final int USER_IS_ALREADY_REGISTERED_STATUS_CODE = 404;
    private static final String USER_IS_ALREADY_REGISTERED = "User is already registered";

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return USER_IS_ALREADY_REGISTERED_STATUS_CODE;
    }

    public String getErrorMessage() {
        return USER_IS_ALREADY_REGISTERED;
    }
}