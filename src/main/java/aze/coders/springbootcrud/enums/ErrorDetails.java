package aze.coders.springbootcrud.enums;


public enum ErrorDetails {
    CUSTOMER_NOT_FOUND(404, "Customer not found"),
    TYPE_MISMATCH_EXCEPTION(305, "Type mismatch exception"),
    EXCEPTION(500, "Unknown exception");
    private final Integer code;
    private final String message;

    ErrorDetails(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    ErrorDetails( String message) {
        this.code = 1000;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
