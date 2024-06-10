package aze.coders.springbootcrud.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
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

}
