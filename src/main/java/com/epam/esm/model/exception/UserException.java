package com.epam.esm.model.exception;

public class UserException extends RuntimeException {

    private static final int USER_EXCEPTION_STATUS_CODE = 404;

    public UserException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return USER_EXCEPTION_STATUS_CODE;
    }

}