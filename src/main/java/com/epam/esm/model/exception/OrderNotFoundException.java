package com.epam.esm.model.exception;

public class OrderNotFoundException extends CustomServiceException {

    private static final int ORDER_NOT_FOUND_STATUS_CODE = 404;
    private static final String ORDER_NOT_FOUND_MESSAGE = "Error! Order has been not found!";

    public OrderNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getErrorCode() {
        return ORDER_NOT_FOUND_STATUS_CODE;
    }

    @Override
    public String getErrorMessage() {
        return ORDER_NOT_FOUND_MESSAGE;
    }
}
