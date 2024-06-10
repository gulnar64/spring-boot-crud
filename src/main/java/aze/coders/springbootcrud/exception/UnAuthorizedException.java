package aze.coders.springbootcrud.exception;

import aze.coders.springbootcrud.enums.ErrorDetails;
import lombok.Data;

@Data
public class UnAuthorizedException extends RuntimeException {

    private final Integer code;
    private final String message;

    public UnAuthorizedException(ErrorDetails errorDetails) {
        super(errorDetails.getMessage());
        this.code = errorDetails.getCode();
        this.message = errorDetails.getMessage();
    }
}
