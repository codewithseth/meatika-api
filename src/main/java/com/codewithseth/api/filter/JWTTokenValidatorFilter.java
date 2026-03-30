package com.codewithseth.api.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codewithseth.api.constant.AppConstants;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private final Environment env;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AppConstants.JWT_HEADER); // "Authorization"

        if (authHeader != null) {
            try {
                String jwt = authHeader.substring(7); // Remove "Bearer " prefix

                if (env != null) {
                    String secret = env.getProperty("jwt.secret");
                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                    if (secretKey != null) {
                        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
                        String username = String.valueOf(claims.get("username"));

                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                            username, null, AuthorityUtils.NO_AUTHORITIES
                        );

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (ExpiredJwtException exception) {
                log.error("JWT token has expired: {}", exception.getMessage());
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
                return;
            } catch (UnsupportedJwtException exception) {
                log.error("Unsupported JWT token: {}", exception.getMessage());
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
                return;
            } catch (MalformedJwtException exception) {
                log.error("Malformed JWT token: {}", exception.getMessage()); // tested
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
                return;
            } catch (SignatureException exception) {
                log.error("Invalid JWT signature: {}", exception.getMessage()); //tested
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
                return;

            } catch (IllegalArgumentException exception) {
                log.error("JWT token is null or empty: {}", exception.getMessage());
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
                return;
            } catch (Exception exception) {
                log.error("Error validating JWT token: {}", exception.getMessage());
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        return path.startsWith("/api/v1/auth") ||
               path.startsWith("/v3/api-docs") ||
               path.startsWith("/swagger-ui");
    }

    private static final String ERROR_MESSAGE = "An error occurred while validating the JWT token";
    private void sendErrorResponse(HttpServletResponse response, int code, String data) throws IOException {
        response.setStatus(code);
        response.setContentType("application/json");
        
        String jsonResponse = String.format(
            "{\"flag\": false, \"code\": %d, \"message\": \"%s\", \"data\": \"%s\"}", 
            code, ERROR_MESSAGE, data
        );

        response.getWriter().write(jsonResponse);
    }

}
