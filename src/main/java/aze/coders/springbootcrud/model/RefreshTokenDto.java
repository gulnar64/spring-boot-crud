package aze.coders.springbootcrud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {
    private Long id;
    private String token;
    private String username;
    private Boolean valid;
    private Date issueDate;
    private Date expireDate;
}
