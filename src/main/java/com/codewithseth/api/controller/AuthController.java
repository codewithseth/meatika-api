package com.codewithseth.api.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithseth.api.dto.ErrorResponse;
import com.codewithseth.api.dto.auth.LoginDto;
import com.codewithseth.api.dto.auth.LoginReqDto;
import com.codewithseth.api.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@Valid @RequestBody LoginReqDto loginReqDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReqDto.username(), loginReqDto.password())
            );

            String jwtToken = jwtUtil.generateJwtToken(authenticate);

            LoginDto loginDto = new LoginDto(jwtToken);

            return ResponseEntity.ok(loginDto);
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (Exception ex) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        ErrorResponse errorResponse = new ErrorResponse(
            "",
            status,
            message,
            LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

} 
