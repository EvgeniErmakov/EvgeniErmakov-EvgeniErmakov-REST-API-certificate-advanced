package com.epam.esm.controller.exception;

import com.epam.esm.controller.model.ErrorResponse;
import com.epam.esm.model.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestControllerAdvice
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    private final MessageSource messageSource;

    private static final int SERVER_ERROR_CODE = 500;
    private static final int AUTHENTICATION_ERROR_CODE = 401;
    private static final int BIND_EXCEPTION_ERROR_CODE = 102;
    private static final int CONSTRAINT_VIOLATION_ERROR_CODE = 101;
    private static final String SERVER_MESSAGE = "ServerError";
    private static final String USER_NOT_FOUND_ERROR = "AuthenticationError";
    private static final String DELIMITER = " ";

/*
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<Object> serverError(Locale locale) {
            String message = messageSource.getMessage(SERVER_MESSAGE, new Object[]{}, locale);
            return new ResponseEntity<>(createErrorResponse(message, SERVER_ERROR_CODE),

                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
 */

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException exception,
        Locale locale) {
        String message = messageSource.getMessage(exception.getErrorMessage(), new Object[]{},
            locale);
        String errorMessage = message + DELIMITER + exception.getMessage();
        return new ResponseEntity<>(createErrorResponse(errorMessage, exception.getErrorCode()),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> bindExceptionException(BindException exception, Locale locale) {
        CustomErrorResponse customErrorResponse = CustomErrorResponse.builder()
            .errorMessage(exception.getBindingResult().getAllErrors()
                .stream()
                .collect(
                    Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        error -> messageSource.getMessage(error, locale),
                        (existing, replacement) -> existing
                    )
                )
            )
            .errorStatusCode(BIND_EXCEPTION_ERROR_CODE)
            .build();
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
        ConstraintViolationException exception) {
        CustomErrorResponse customErrorResponse = CustomErrorResponse.builder()
            .errorMessage(exception.getConstraintViolations()
                .stream()
                .collect(
                    Collectors.toMap(constraintViolation -> StreamSupport
                            .stream(constraintViolation.getPropertyPath().spliterator(), false)
                            .reduce((x, y) -> y).orElse(null),
                        ConstraintViolation::getMessage,
                        (existing, replacement) -> existing
                    )
                ))
            .errorStatusCode(CONSTRAINT_VIOLATION_ERROR_CODE)
            .build();
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse createErrorResponse(String errorMessage, int errorCode) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage);
        response.setErrorStatusCode(errorCode);
        return response;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception,
        Locale locale) {
        String message = messageSource.getMessage(USER_NOT_FOUND_ERROR, new Object[]{}, locale);
        return new ResponseEntity<>(createErrorResponse(message, AUTHENTICATION_ERROR_CODE),
            HttpStatus.UNAUTHORIZED);
    }
}
