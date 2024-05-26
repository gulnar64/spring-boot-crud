package aze.coders.springbootcrud;

import aze.coders.springbootcrud.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Main {
    private String secret = "RlgxVlRSVklpTENKU1QweEZYMEZFVFVsT0lsMHNJbUZqWTI5MWJuUk9iMjVGZUhCcGNtVmtJanAwYw==";

    public static void main(String[] args) {

        //Builderla olan xeta

        Main main = new Main();
        String token = main.createToken();
        System.out.println(token);
        System.out.println("***************************");
        System.out.println(main.readToken(token));

    }

    public String createToken() {
        return Jwts.builder().header().add(Map.of("typ", "JWT", "alg", "HS256")).and().claims().subject("user").issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 1000 * 60 + 24)).add("roles", List.of("ROLES_ADMIN", "ROLES_USER")).add("username", "user").and().signWith(getSigningKey()).compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Object readToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parse(token).getPayload();
    }

}
