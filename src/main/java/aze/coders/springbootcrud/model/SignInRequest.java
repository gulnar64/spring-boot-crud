package aze.coders.springbootcrud.model;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}
