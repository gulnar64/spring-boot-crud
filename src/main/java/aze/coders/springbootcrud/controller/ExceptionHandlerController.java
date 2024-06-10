package aze.coders.springbootcrud.controller;

import aze.coders.springbootcrud.enums.ErrorDetails;
import aze.coders.springbootcrud.exception.CustomerNotFoundException;
import aze.coders.springbootcrud.exception.UnAuthorizedException;
import aze.coders.springbootcrud.model.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ErrorResponse handleCustomerNotFoundException(CustomerNotFoundException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ErrorResponse handleUnAuthorizedException(UnAuthorizedException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new ErrorResponse(ErrorDetails.TYPE_MISMATCH_EXCEPTION.getCode(), ErrorDetails.TYPE_MISMATCH_EXCEPTION.getMessage() + ", ex: " + e.getMessage() + ", property: " + e.getPropertyName());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse(ErrorDetails.EXCEPTION.getCode(), ErrorDetails.EXCEPTION.getMessage() + ", ex: " + e.getMessage());
    }
}
