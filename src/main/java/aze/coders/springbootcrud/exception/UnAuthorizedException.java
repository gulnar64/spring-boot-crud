package aze.coders.springbootcrud.exception;

import aze.coders.springbootcrud.enums.ErrorDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

Data
public class UnAuthorizedException extends RuntimeException {

    private Integer code;
    private String message;

    public UnAuthorizedException(ErrorDetails errorDetails) {
        super(errorDetails.getMessage());
        this.code = errorDetails.getCode();
        this.message = errorDetails.getMessage();
    }
}
