package com.epam.esm.model.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final int Entity_NOT_FOUND_STATUS_CODE = 404;
    private static final String Entity_NOT_FOUND_MESSAGE = "Error! Entity has been not found!";

    public  EntityNotFoundException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return Entity_NOT_FOUND_STATUS_CODE;
    }

    public String getErrorMessage() {
        return Entity_NOT_FOUND_MESSAGE;
    }
}
