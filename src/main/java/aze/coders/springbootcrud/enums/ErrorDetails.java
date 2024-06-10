package aze.coders.springbootcrud.enums;


public enum ErrorDetails {
    CUSTOMER_NOT_FOUND(404, "Customer not found"),
    TYPE_MISMATCH_EXCEPTION(305, "Type mismatch exception"),
    EXCEPTION(500, "Unknown exception"),
    REFRESH_TOKEN_NOT_FOUND(405, "Refresh token not found"),
    REFRESH_TOKEN_IS_NOT_VALID(406, "Refresh token is not valid"),
    REFRESH_TOKEN_IS_EXPIRED(406, "Refresh token is expired"),
    USER_DETAIL_NOT_VALID(406, "User detail not valid");
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
