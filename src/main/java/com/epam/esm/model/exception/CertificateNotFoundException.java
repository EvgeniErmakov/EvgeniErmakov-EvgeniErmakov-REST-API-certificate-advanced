package com.epam.esm.model.exception;

public class CertificateNotFoundException extends CustomServiceException {

    private static final int CERTIFICATE_NOT_FOUND_STATUS_CODE = 404;
    private static final String CERTIFICATE_NOT_FOUND_MESSAGE = "Error! Certificate has been not found!";

    public CertificateNotFoundException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return CERTIFICATE_NOT_FOUND_STATUS_CODE;
    }

    public String getErrorMessage() {
        return CERTIFICATE_NOT_FOUND_MESSAGE;
    }
}
