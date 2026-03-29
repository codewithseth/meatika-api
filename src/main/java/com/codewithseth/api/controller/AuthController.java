package com.codewithseth.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithseth.api.dto.auth.LoginDto;
import com.codewithseth.api.dto.auth.LoginReqDto;
import com.codewithseth.api.system.ResultResponse;
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
    public ResponseEntity<ResultResponse> loginAccount(@Valid @RequestBody LoginReqDto loginReqDto) {
        // Authenticate the user using the provided credentials
        Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginReqDto, loginReqDto.password())
        );

        // Generate a JWT token for the authenticated user
        String accessToken = jwtUtil.generateJwtToken(authenticate);

        // Create a response containing the access token and return it to the client
        LoginDto loginDto = new LoginDto(accessToken);
        ResultResponse response = new ResultResponse(true, 200, "Login successful", loginDto);
        return ResponseEntity.ok(response);
    }

}
