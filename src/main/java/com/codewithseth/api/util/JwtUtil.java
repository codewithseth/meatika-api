package com.codewithseth.api.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.codewithseth.api.entity.Account;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Environment env;

    public String generateJwtToken(Authentication authentication) {
        String jwt = "";
        
        String secret = env.getProperty("jwt.secret");
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        Account account = (Account) authentication.getPrincipal();

        jwt = Jwts.builder().issuer("Meatika").subject("JWT Token")
            .claim("username", account.getUsername())
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + 24 * 60 * 60 * 1000)) // 24 hours
            .signWith(secretKey)
            .compact();

        return jwt;
    }

}
