package aze.coders.springbootcrud.exception;

import aze.coders.springbootcrud.enums.ErrorDetails;
import lombok.Data;

@Data
public class CustomerNotFoundException extends RuntimeException {

    private Integer code;
    private String message;

    public CustomerNotFoundException(ErrorDetails errorDetails) {
        super(errorDetails.getMessage());
        this.code = errorDetails.getCode();
        this.message = errorDetails.getMessage();
    }
}
