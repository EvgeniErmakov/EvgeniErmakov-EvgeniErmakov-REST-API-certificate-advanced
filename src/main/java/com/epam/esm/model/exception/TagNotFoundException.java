package com.epam.esm.model.exception;

public class TagNotFoundException extends CustomServiceException {

    private static final int TAG_NOT_FOUND_STATUS_CODE = 404;
    private static final String TAG_NOT_FOUND_MESSAGE = "Error! Tag has been not found!";

    public TagNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getErrorCode() {
        return TAG_NOT_FOUND_STATUS_CODE;
    }

    @Override
    public String getErrorMessage() {
        return TAG_NOT_FOUND_MESSAGE;
    }
}
