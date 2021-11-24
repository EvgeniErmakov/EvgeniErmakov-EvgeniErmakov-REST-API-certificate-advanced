package com.epam.esm.model.exception;

public class UserNotFoundException extends CustomServiceException {

    private static final int USER_NOT_FOUND_STATUS_CODE = 404;
    private static final String USER_NOT_FOUND_MESSAGE =  "Error! User has been not found!";

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getErrorCode() {
        return USER_NOT_FOUND_STATUS_CODE;
    }

    @Override
    public String getErrorMessage() {
        return USER_NOT_FOUND_MESSAGE;
    }
}
