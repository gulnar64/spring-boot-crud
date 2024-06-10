package aze.coders.springbootcrud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String username;
    private Boolean valid;
    private Date issueDate;
    private Date expireDate;

    public RefreshToken(String token, String username, Boolean valid, Date issueDate, Date expireDate) {
        this.token = token;
        this.username = username;
        this.valid = valid;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
    }
}
